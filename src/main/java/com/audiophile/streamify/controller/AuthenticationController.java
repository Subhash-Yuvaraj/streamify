package com.audiophile.streamify.controller;

import com.audiophile.streamify.dto.JWTAuthenticationResponse;
import com.audiophile.streamify.dto.RefreshTokenRequest;
import com.audiophile.streamify.dto.SigninRequest;
import com.audiophile.streamify.dto.SignupRequest;
import com.audiophile.streamify.model.User;
import com.audiophile.streamify.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/signup-listener")
    public ResponseEntity<User> signupListener(@RequestBody SignupRequest signupRequest){
        return ResponseEntity.ok(authenticationService.signupListener(signupRequest));
    }

    @PostMapping("/signup-artist")
    public ResponseEntity<User> signupArtist(@RequestBody SignupRequest signupRequest){
        return ResponseEntity.ok(authenticationService.signupArtist(signupRequest));
    }

    @PostMapping("/signin")
    public ResponseEntity<JWTAuthenticationResponse> signin(@RequestBody SigninRequest signinRequest){
        return ResponseEntity.ok(authenticationService.signin(signinRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JWTAuthenticationResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest){
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
    }
}
