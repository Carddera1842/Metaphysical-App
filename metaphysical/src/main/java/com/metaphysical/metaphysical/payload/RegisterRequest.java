package com.metaphysical.metaphysical.payload;

import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String password;
    private String displayName;
}