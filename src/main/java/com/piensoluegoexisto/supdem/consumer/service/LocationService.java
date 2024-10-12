package com.piensoluegoexisto.supdem.consumer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.piensoluegoexisto.supdem.consumer.db.model.Location;
import com.piensoluegoexisto.supdem.consumer.db.repository.LocationRepository;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public List<Location> getLocationsNear(double longitude, double latitude, double distance) {
        return locationRepository.findWithinDistance(longitude, latitude, distance);
    }
}