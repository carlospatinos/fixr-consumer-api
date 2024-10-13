package com.piensoluegoexisto.supdem.consumer.db.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.piensoluegoexisto.supdem.consumer.db.model.Geofence;

@Repository
public interface GeofenceRepository extends CrudRepository<Geofence, UUID> {
    // TODO remove the schema from the query
    @Query(value = "SELECT g.* FROM fixr_app.geofences g WHERE ST_Intersects(g.geometry_polygon, ST_SetSRID(ST_MakePoint(:lng, :lat), 4326))", nativeQuery = true)
    public List<Geofence> findAllByLngLat(Double lng, Double lat);
}
