package com.descentrilizedsynergy.supdem.consumer.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class ConsumerProfileResponse {
    private List<ConsumerProfileRequest> profiles;
    private ConsumerProfileRequest singleProfile;
}
