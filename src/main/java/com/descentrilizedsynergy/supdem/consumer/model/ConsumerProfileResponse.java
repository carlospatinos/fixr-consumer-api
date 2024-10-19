package com.descentrilizedsynergy.supdem.consumer.model;

import java.util.List;

import com.descentrilizedsynergy.supdem.consumer.db.model.ConsumerProfile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class ConsumerProfileResponse {
    private List<ConsumerProfile> profiles;
    private ConsumerProfile singleProfile;
}
