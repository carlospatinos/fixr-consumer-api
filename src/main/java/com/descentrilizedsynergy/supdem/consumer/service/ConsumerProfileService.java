package com.descentrilizedsynergy.supdem.consumer.service;

import java.util.List;
import java.util.stream.Collectors;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.util.GeometricShapeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import com.descentrilizedsynergy.supdem.consumer.db.model.ConsumerProfile;
import com.descentrilizedsynergy.supdem.consumer.db.repository.ConsumerProfileRepository;
import com.descentrilizedsynergy.supdem.consumer.model.ConsumerProfileRequest;
import com.descentrilizedsynergy.supdem.consumer.model.ConsumerProfileResponse;

@Service
public class ConsumerProfileService {

        @Autowired
        private ConsumerProfileRepository profileRepository;

        // TODO this works with both h2 and postgres but if we generate the covered area
        // then fails
        public ConsumerProfileResponse createProfileWithPolygon(ConsumerProfileRequest requestBody) {
                ConsumerProfile newProfile = new ConsumerProfile(requestBody.getName(), requestBody.getLastName(),
                                requestBody.getEmail(),
                                requestBody.getTravelDistanceInMeters(), requestBody.getExactLocation(),
                                requestBody.getCoveredArea());

                newProfile.setDescription(requestBody.getDescription());
                newProfile.setDesiredHourlyRate(requestBody.getDesiredHourlyRate());
                newProfile.setCurrency(requestBody.getCurrency());

                ConsumerProfile createdProfile = profileRepository.save(newProfile);

                return new ConsumerProfileResponse(createdProfile.getId(), createdProfile.getName(),
                                createdProfile.getLastName(),
                                createdProfile.getEmail(),
                                createdProfile.getDescription(),
                                createdProfile.getDesiredHourlyRate(), createdProfile.getCoveredArea(),
                                createdProfile.getExactLocation());
        }

        public ConsumerProfileResponse createProfile(ConsumerProfileRequest requestBody) {
                double latitude = requestBody.getExactLocation().getX(); // 40.689234d;
                double longitude = requestBody.getExactLocation().getY(); // -74.044598d;
                double diameterInMeters = requestBody.getTravelDistanceInMeters(); // 2000d; // 2km

                GeometricShapeFactory shapeFactory = new GeometricShapeFactory(
                                new GeometryFactory(new PrecisionModel(PrecisionModel.FLOATING), 4326));

                shapeFactory.setNumPoints(64); // adjustable
                shapeFactory.setCentre(new Coordinate(latitude, longitude));
                // Length in meters of 1° of latitude = always 111.32 km
                shapeFactory.setWidth(diameterInMeters / 111320d);
                // Length in meters of 1° of longitude = 40075 km * cos( latitude ) / 360
                shapeFactory.setHeight(diameterInMeters / (40075000 * Math.cos(Math.toRadians(latitude)) / 360));

                Polygon coveredArea = shapeFactory.createCircle();

                ConsumerProfile newProfile = new ConsumerProfile(requestBody.getName(), requestBody.getLastName(),
                                requestBody.getEmail(),
                                requestBody.getTravelDistanceInMeters(), requestBody.getExactLocation(),
                                coveredArea);

                newProfile.setDescription(requestBody.getDescription());
                newProfile.setDesiredHourlyRate(requestBody.getDesiredHourlyRate());
                newProfile.setCurrency(requestBody.getCurrency());

                ConsumerProfile createdProfile = profileRepository.save(newProfile);

                return new ConsumerProfileResponse(createdProfile.getId(), createdProfile.getName(),
                                createdProfile.getLastName(),
                                createdProfile.getEmail(),
                                createdProfile.getDescription(),
                                createdProfile.getDesiredHourlyRate(),
                                createdProfile.getCoveredArea(),
                                createdProfile.getExactLocation());
        }

        public List<ConsumerProfileResponse> getProfiles() {

                Iterable<ConsumerProfile> profiles = profileRepository.findAll();

                // TODO change transformation logic.
                List<ConsumerProfile> list = Streamable.of(profiles).toList();
                List<ConsumerProfileResponse> l2 = list.stream()
                                .map(g -> new ConsumerProfileResponse(g.getId(), g.getName(), g.getLastName(),
                                                g.getEmail(),
                                                g.getDescription(),
                                                g.getDesiredHourlyRate(),
                                                g.getCoveredArea(),
                                                g.getExactLocation()))
                                .collect(Collectors.toList());

                return l2;
        }

        public List<ConsumerProfileResponse> getProfileByProximityToLocation(Double latitude, Double longitude) {
                Iterable<ConsumerProfile> profilesNearby = profileRepository.findAllByLngLat(latitude, longitude);

                List<ConsumerProfile> list = Streamable.of(profilesNearby).toList();
                List<ConsumerProfileResponse> l2 = list.stream()
                                .map(g -> new ConsumerProfileResponse(g.getId(), g.getName(), g.getLastName(),
                                                g.getEmail(),
                                                g.getDescription(),
                                                g.getDesiredHourlyRate(),
                                                g.getCoveredArea(),
                                                g.getExactLocation()))
                                .collect(Collectors.toList());

                return l2;
        }
}
