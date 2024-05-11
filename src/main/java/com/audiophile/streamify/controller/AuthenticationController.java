package com.audiophile.streamify.controller;

import com.audiophile.streamify.dto.JWTAuthenticationResponse;
import com.audiophile.streamify.dto.RefreshTokenRequest;
import com.audiophile.streamify.dto.SigninRequestDTO;
import com.audiophile.streamify.dto.SignupRequestDTO;
import com.audiophile.streamify.model.User;
import com.audiophile.streamify.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;



    @PostMapping("/signup-listener")
    public ResponseEntity<User> signupListener(@RequestBody SignupRequestDTO signupRequestDTO){
        return ResponseEntity.ok(authenticationService.signupListener(signupRequestDTO));
    }

    @PostMapping("/signup-artist")
    public ResponseEntity<User> signupArtist(@RequestBody SignupRequestDTO signupRequestDTO){
        return ResponseEntity.ok(authenticationService.signupArtist(signupRequestDTO));
    }

    @PostMapping("/signin")
    public ResponseEntity<JWTAuthenticationResponse> signin(@RequestBody SigninRequestDTO signinRequestDTO){
        return ResponseEntity.ok(authenticationService.signin(signinRequestDTO));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JWTAuthenticationResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest){
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
    }
}
