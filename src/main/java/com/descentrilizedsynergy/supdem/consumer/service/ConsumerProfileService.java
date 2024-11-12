package com.descentrilizedsynergy.supdem.consumer.service;

import java.util.ArrayList;
import java.util.List;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.descentrilizedsynergy.supdem.consumer.db.model.ConsumerProfile;
import com.descentrilizedsynergy.supdem.consumer.db.repository.ConsumerProfileRepository;
import com.descentrilizedsynergy.supdem.consumer.model.ConsumerProfileRequest;
import com.descentrilizedsynergy.supdem.consumer.model.ConsumerProfileResponse;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ConsumerProfileService {
        private ModelMapper modelMapper;

        @Autowired
        private ConsumerProfileRepository profileRepository;

        @Autowired
        private PasswordEncoder passwordEncoder;

        @PostConstruct
        public void init() {
                modelMapper = new ModelMapper();
                modelMapper.getConfiguration().setSkipNullEnabled(true)
                                .setMatchingStrategy(MatchingStrategies.STRICT);
        }

        public ConsumerProfileResponse createProfile(ConsumerProfileRequest requestBody) {
                double latitude = requestBody.getLatitude();
                double longitude = requestBody.getLongitude();

                GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(PrecisionModel.FLOATING),
                                4326);
                Point exactLocation = geometryFactory.createPoint(new Coordinate(latitude, longitude));

                ConsumerProfile newProfile = new ConsumerProfile(requestBody.getName(), requestBody.getLastName(),
                                requestBody.getEmail(), exactLocation);

                newProfile.setDescription(requestBody.getDescription());
                newProfile.setLatitude(latitude);
                newProfile.setLongitude(longitude);
                newProfile.setAddress(requestBody.getAddress());
                newProfile.setPassword(passwordEncoder.encode(requestBody.getPassword()));

                ConsumerProfileResponse response = new ConsumerProfileResponse();
                try {
                        ConsumerProfile createdProfile = profileRepository.save(newProfile);
                        ConsumerProfileRequest copy = new ConsumerProfileRequest();
                        modelMapper.map(createdProfile, copy);
                        response.setSingleProfile(copy);
                } catch (DataIntegrityViolationException ex) {
                        log.error("Profile already exist", ex.getMessage());
                        response.setError("Profile already exist");
                } catch (Exception ex) {
                        log.error("Profile creation error", ex);
                        response.setError(ex.getMessage());
                }

                return response;
        }

        public ConsumerProfileResponse getProfiles() {
                // TODO add pagination
                List<ConsumerProfile> allProfiles = profileRepository.findAll();
                ConsumerProfileResponse response = new ConsumerProfileResponse();

                List<ConsumerProfileRequest> toRequests = new ArrayList<>();
                for (ConsumerProfile entityProfile : allProfiles) {
                        ConsumerProfileRequest copy = new ConsumerProfileRequest();
                        // BeanUtils.copyProperties(copy, entityProfile);
                        ModelMapper modelMapper = new ModelMapper();
                        modelMapper.getConfiguration().setSkipNullEnabled(true)
                                        .setMatchingStrategy(MatchingStrategies.STRICT);
                        modelMapper.map(entityProfile, copy);
                        toRequests.add(copy);
                }

                response.setProfiles(toRequests);
                return response;
        }
}
