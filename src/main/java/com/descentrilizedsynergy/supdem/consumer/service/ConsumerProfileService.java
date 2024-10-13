package com.descentrilizedsynergy.supdem.consumer.service;

import java.util.List;
import java.util.stream.Collectors;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Polygon;
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
        private ConsumerProfileRepository geofenceRepository;

        public ConsumerProfileResponse createGeofenceUseCase(ConsumerProfileRequest requestBody) {
                ConsumerProfile newGeofence = new ConsumerProfile(requestBody.getName(),
                                requestBody.getGeometryPolygon(),
                                requestBody.getGeometryPoint());

                ConsumerProfile geofence = geofenceRepository.save(newGeofence);

                return new ConsumerProfileResponse(geofence.getId(), geofence.getName(), geofence.getGeometryPolygon(),
                                geofence.getGeometryPoint());
        }

        public ConsumerProfileResponse createGeofenceUseCaseLatLongDiam(ConsumerProfileRequest requestBody) {
                double latitude = requestBody.getGeometryPoint().getX(); // 40.689234d;
                double longitude = requestBody.getGeometryPoint().getY(); // -74.044598d;
                double diameterInMeters = requestBody.getDiameterInMeters(); // 2000d; // 2km

                GeometricShapeFactory shapeFactory = new GeometricShapeFactory();
                shapeFactory.setNumPoints(64); // adjustable
                shapeFactory.setCentre(new Coordinate(latitude, longitude));
                // Length in meters of 1° of latitude = always 111.32 km
                shapeFactory.setWidth(diameterInMeters / 111320d);
                // Length in meters of 1° of longitude = 40075 km * cos( latitude ) / 360
                shapeFactory.setHeight(diameterInMeters / (40075000 * Math.cos(Math.toRadians(latitude)) / 360));

                Polygon generatedPolygon = shapeFactory.createEllipse();

                ConsumerProfile newGeofence = new ConsumerProfile(requestBody.getName(),
                                generatedPolygon,
                                requestBody.getGeometryPoint());

                ConsumerProfile geofence = geofenceRepository.save(newGeofence);

                return new ConsumerProfileResponse(geofence.getId(), geofence.getName(), geofence.getGeometryPolygon(),
                                geofence.getGeometryPoint());
        }

        public List<ConsumerProfileResponse> listGeofenceUseCase() {

                Iterable<ConsumerProfile> geofence = geofenceRepository.findAll();

                List<ConsumerProfile> list = Streamable.of(geofence).toList();
                List<ConsumerProfileResponse> l2 = list.stream()
                                .map(g -> new ConsumerProfileResponse(g.getId(), g.getName(), g.getGeometryPolygon(),
                                                g.getGeometryPoint()))
                                .collect(Collectors.toList());

                return l2;
        }

        public List<ConsumerProfileResponse> getNearbyService(Double latitude, Double longitude) {
                Iterable<ConsumerProfile> geofence = geofenceRepository.findAllByLngLat(latitude, longitude);

                List<ConsumerProfile> list = Streamable.of(geofence).toList();
                List<ConsumerProfileResponse> l2 = list.stream()
                                .map(g -> new ConsumerProfileResponse(g.getId(), g.getName(), g.getGeometryPolygon(),
                                                g.getGeometryPoint()))
                                .collect(Collectors.toList());

                return l2;
        }
}
