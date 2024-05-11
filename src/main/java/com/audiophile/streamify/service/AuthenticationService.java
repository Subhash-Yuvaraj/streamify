package com.audiophile.streamify.service;

import com.audiophile.streamify.dto.JWTAuthenticationResponse;
import com.audiophile.streamify.dto.RefreshTokenRequest;
import com.audiophile.streamify.dto.SigninRequestDTO;
import com.audiophile.streamify.dto.SignupRequestDTO;
import com.audiophile.streamify.model.User;

public interface AuthenticationService {
    User signupArtist(SignupRequestDTO signupRequestDTO);

    User signupListener(SignupRequestDTO signupRequestDTO);

    JWTAuthenticationResponse signin(SigninRequestDTO signinRequestDTO);

    JWTAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
