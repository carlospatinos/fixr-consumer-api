package com.descentrilizedsynergy.supdem.consumer.service;

import java.util.List;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.descentrilizedsynergy.supdem.consumer.db.model.ConsumerProfile;
import com.descentrilizedsynergy.supdem.consumer.db.repository.ConsumerProfileRepository;
import com.descentrilizedsynergy.supdem.consumer.model.ConsumerProfileRequest;
import com.descentrilizedsynergy.supdem.consumer.model.ConsumerProfileResponse;

@Service
public class ConsumerProfileService {

        @Autowired
        private ConsumerProfileRepository profileRepository;

        public ConsumerProfileResponse createProfile(ConsumerProfileRequest requestBody) {
                double latitude = requestBody.getLatitude(); // requestBody.getExactLocation().getX(); // 40.689234d;
                double longitude = requestBody.getLongitude(); // requestBody.getExactLocation().getY(); // -74.044598d;

                GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(PrecisionModel.FLOATING),
                                4326);
                Point exactLocation = geometryFactory.createPoint(new Coordinate(latitude, longitude));

                ConsumerProfile newProfile = new ConsumerProfile(requestBody.getName(), requestBody.getLastName(),
                                requestBody.getEmail(), exactLocation);

                newProfile.setDescription(requestBody.getDescription());
                newProfile.setLatitude(latitude);
                newProfile.setLongitude(longitude);
                newProfile.setAddress(requestBody.getAddress());

                ConsumerProfile createdProfile = profileRepository.save(newProfile);
                ConsumerProfileResponse response = new ConsumerProfileResponse();
                response.setSingleProfile(createdProfile);
                return response;
        }

        public ConsumerProfileResponse getProfiles() {
                // TODO add pagination
                List<ConsumerProfile> allProfiles = profileRepository.findAll();
                ConsumerProfileResponse response = new ConsumerProfileResponse();
                response.setProfiles(allProfiles);
                return response;
        }

        public ConsumerProfileResponse getProfileByProximityToLocation(Double latitude, Double longitude) {
                List<ConsumerProfile> profilesNearby = profileRepository.findAllByLngLat(latitude, longitude);
                ConsumerProfileResponse response = new ConsumerProfileResponse();
                response.setProfiles(profilesNearby);
                return response;
        }
}
