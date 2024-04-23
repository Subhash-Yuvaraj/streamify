package com.audiophile.streamify.service.impl;

import com.audiophile.streamify.model.User;
import com.audiophile.streamify.repository.UserRepository;
import com.audiophile.streamify.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository ;
    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean checkUserEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent();
    }

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository.findByEmail(username)
                        .orElseThrow(()-> new UsernameNotFoundException("No user found"));
            }
        };
    }
}
