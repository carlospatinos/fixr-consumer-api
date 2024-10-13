package com.piensoluegoexisto.supdem.consumer.service;

import java.util.List;
import java.util.stream.Collectors;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.util.GeometricShapeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import com.piensoluegoexisto.supdem.consumer.db.model.CreateGeofenceRequest;
import com.piensoluegoexisto.supdem.consumer.db.model.Geofence;
import com.piensoluegoexisto.supdem.consumer.db.model.GeofenceResponse;
import com.piensoluegoexisto.supdem.consumer.db.repository.GeofenceRepository;

@Service
public class CreateGeofenceUseCase {

    @Autowired
    private GeofenceRepository geofenceRepository;

    public GeofenceResponse createGeofenceUseCase(CreateGeofenceRequest requestBody) {
        Geofence newGeofence = new Geofence(requestBody.getName(),
                requestBody.getGeometryPolygon(),
                requestBody.getGeometryPoint());

        Geofence geofence = geofenceRepository.save(newGeofence);

        return new GeofenceResponse(geofence.getId(), geofence.getName(), geofence.getGeometryPolygon(),
                geofence.getGeometryPoint());
    }

    public GeofenceResponse createGeofenceUseCaseLatLongDiam(CreateGeofenceRequest requestBody) {
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

        Geofence newGeofence = new Geofence(requestBody.getName(),
                generatedPolygon,
                requestBody.getGeometryPoint());

        Geofence geofence = geofenceRepository.save(newGeofence);

        return new GeofenceResponse(geofence.getId(), geofence.getName(), geofence.getGeometryPolygon(),
                geofence.getGeometryPoint());
    }

    public List<GeofenceResponse> listGeofenceUseCase() {

        Iterable<Geofence> geofence = geofenceRepository.findAll();

        List<Geofence> list = Streamable.of(geofence).toList();
        List<GeofenceResponse> l2 = list.stream()
                .map(g -> new GeofenceResponse(g.getId(), g.getName(), g.getGeometryPolygon(),
                        g.getGeometryPoint()))
                .collect(Collectors.toList());

        return l2;
    }

    public List<GeofenceResponse> getNearbyService(Double latitude, Double longitude) {
        Iterable<Geofence> geofence = geofenceRepository.findAllByLngLat(latitude, longitude);

        List<Geofence> list = Streamable.of(geofence).toList();
        List<GeofenceResponse> l2 = list.stream()
                .map(g -> new GeofenceResponse(g.getId(), g.getName(), g.getGeometryPolygon(),
                        g.getGeometryPoint()))
                .collect(Collectors.toList());

        return l2;
    }
}
