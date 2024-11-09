package com.descentrilizedsynergy.supdem.consumer.db.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.descentrilizedsynergy.supdem.consumer.db.model.ConsumerProfile;

@Repository
public interface ConsumerProfileRepository extends JpaRepository<ConsumerProfile, UUID> {
    public Optional<ConsumerProfile> findByEmail(String email);

}
