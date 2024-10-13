package com.piensoluegoexisto.supdem.consumer.service;

import java.util.List;
import java.util.stream.Collectors;

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
