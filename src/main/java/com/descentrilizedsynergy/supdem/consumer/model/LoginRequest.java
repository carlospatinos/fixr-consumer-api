package com.descentrilizedsynergy.supdem.consumer.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class LoginRequest {
    private String email;

    @ToString.Exclude
    private String password;
}
