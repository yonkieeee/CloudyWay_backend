package com.example.authuser.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForgetPassword {
    private String email;
    private String otp;
}
