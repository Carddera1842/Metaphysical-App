package com.metaphysical.metaphysical.payload;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
