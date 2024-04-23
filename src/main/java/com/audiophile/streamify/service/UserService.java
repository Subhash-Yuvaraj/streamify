package com.audiophile.streamify.service;

import com.audiophile.streamify.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Map;

public interface UserService {
    User createUser(User user);
    boolean checkUserEmail(String email);

    UserDetailsService userDetailsService();
}
