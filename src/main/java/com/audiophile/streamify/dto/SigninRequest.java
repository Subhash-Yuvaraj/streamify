package com.audiophile.streamify.dto;

import lombok.Data;

@Data
public class SigninRequest {
    private String password;
    private String email;
}
