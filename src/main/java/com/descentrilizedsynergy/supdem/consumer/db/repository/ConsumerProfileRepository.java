package com.descentrilizedsynergy.supdem.consumer.db.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.descentrilizedsynergy.supdem.consumer.db.model.ConsumerProfile;

@Repository
public interface ConsumerProfileRepository extends CrudRepository<ConsumerProfile, UUID> {
    // TODO remove the schema from the query
    @Query(value = "SELECT g.* FROM fixr_app.geofences g WHERE ST_Intersects(g.geometry_polygon, ST_SetSRID(ST_MakePoint(:lng, :lat), 4326))", nativeQuery = true)
    public List<ConsumerProfile> findAllByLngLat(Double lng, Double lat);
}
