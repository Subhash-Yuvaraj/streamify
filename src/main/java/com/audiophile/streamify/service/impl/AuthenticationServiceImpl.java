package com.audiophile.streamify.service.impl;
import com.audiophile.streamify.dto.JWTAuthenticationResponse;
import com.audiophile.streamify.dto.RefreshTokenRequest;
import com.audiophile.streamify.dto.SigninRequestDTO;
import com.audiophile.streamify.dto.SignupRequestDTO;
import com.audiophile.streamify.enums.Role;
import com.audiophile.streamify.model.User;
import com.audiophile.streamify.repository.UserRepository;
import com.audiophile.streamify.service.AuthenticationService;
import com.audiophile.streamify.service.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    @Override
    public User signupArtist(SignupRequestDTO signupRequestDTO) {
        User user = new User();
        user.setEmail(signupRequestDTO.getEmail());
        user.setName(signupRequestDTO.getName());
        user.setRole(Role.ARTIST);
        user.setPassword(passwordEncoder.encode(signupRequestDTO.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User signupListener(SignupRequestDTO signupRequestDTO){
        User user = new User();
        user.setEmail(signupRequestDTO.getEmail());
        user.setName(signupRequestDTO.getName());
        user.setRole(Role.LISTENER);
        user.setPassword(passwordEncoder.encode(signupRequestDTO.getPassword()));
        return userRepository.save(user);
    }
    @Override
    public JWTAuthenticationResponse signin(SigninRequestDTO signinRequestDTO){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequestDTO.getEmail()
        , signinRequestDTO.getPassword()));
        Optional<User> user = userRepository.findByEmail(signinRequestDTO.getEmail());
        if(!user.isPresent())
            throw new IllegalArgumentException("Invalid email or password");
        User userObj = user.get();
        var jwt = jwtService.generateToken(userObj);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), userObj);
        JWTAuthenticationResponse jwtAuthenticationResponse = new JWTAuthenticationResponse();
        jwtAuthenticationResponse.setRefreshToken((String) refreshToken);
        jwtAuthenticationResponse.setToken(jwt);
        return jwtAuthenticationResponse;
    }
    @Override
    public JWTAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest){
        String email = jwtService.extractUserName(refreshTokenRequest.getToken());
        Optional<User> user = userRepository.findByEmail(email);
        if(!user.isPresent())
            throw new IllegalArgumentException("Invalid email or token");
        User userObj = user.get();
        if(jwtService.isTokenValid(refreshTokenRequest.getToken(),userObj)){
            var jwt = jwtService.generateToken(userObj);
            JWTAuthenticationResponse jwtAuthenticationResponse = new JWTAuthenticationResponse();
            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
            return jwtAuthenticationResponse;
        }
        return null;
    }
}
