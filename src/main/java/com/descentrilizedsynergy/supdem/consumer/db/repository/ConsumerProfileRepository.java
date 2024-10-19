package com.descentrilizedsynergy.supdem.consumer.db.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.descentrilizedsynergy.supdem.consumer.db.model.ConsumerProfile;

@Repository
public interface ConsumerProfileRepository extends JpaRepository<ConsumerProfile, UUID> {
    @Query(value = "SELECT g.* FROM {h-schema}consumer_profile g WHERE ST_Intersects(g.covered_area, ST_SetSRID(ST_MakePoint(:lng, :lat), 4326))", nativeQuery = true)
    public List<ConsumerProfile> findAllByLngLat(Double lng, Double lat);
}
