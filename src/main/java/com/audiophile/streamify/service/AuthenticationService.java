package com.audiophile.streamify.service;

import com.audiophile.streamify.dto.JWTAuthenticationResponse;
import com.audiophile.streamify.dto.RefreshTokenRequest;
import com.audiophile.streamify.dto.SigninRequest;
import com.audiophile.streamify.dto.SignupRequest;
import com.audiophile.streamify.model.User;

public interface AuthenticationService {
    User signupArtist(SignupRequest signupRequest);

    User signupListener(SignupRequest signupRequest);

    JWTAuthenticationResponse signin(SigninRequest signinRequest);

    JWTAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
