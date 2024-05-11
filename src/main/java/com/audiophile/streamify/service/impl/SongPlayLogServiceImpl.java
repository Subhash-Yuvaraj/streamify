package com.audiophile.streamify.service.impl;

import com.audiophile.streamify.model.Song;
import com.audiophile.streamify.model.SongPlayLog;
import com.audiophile.streamify.model.User;
import com.audiophile.streamify.repository.SongPlayLogRepository;
import com.audiophile.streamify.repository.UserRepository;
import com.audiophile.streamify.service.SongPlayLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SongPlayLogServiceImpl implements SongPlayLogService {

    private final SongPlayLogRepository songPlayLogRepository;
    private final UserRepository userRepository;
    @Override
    public SongPlayLog addLog(Song song){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty())
            throw new RuntimeException("Invalid user");
        User loggedInUser = user.get();
        SongPlayLog songPlayLog = new SongPlayLog();
        songPlayLog.setSong(song);
        songPlayLog.setPlayTime(LocalDateTime.now());
        songPlayLog.setUser(loggedInUser);
        return songPlayLogRepository.save(songPlayLog);
    }
}
