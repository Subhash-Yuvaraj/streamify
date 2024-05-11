package com.audiophile.streamify.dto;

import lombok.Data;

@Data
public class SigninRequestDTO {
    private String password;
    private String email;
}
