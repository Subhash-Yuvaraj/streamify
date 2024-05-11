package com.audiophile.streamify.service.impl;

import com.audiophile.streamify.model.Song;
import com.audiophile.streamify.model.User;
import com.audiophile.streamify.repository.SongPlayLogRepository;
import com.audiophile.streamify.repository.SongRepository;
import com.audiophile.streamify.repository.UserRepository;
import com.audiophile.streamify.service.CronJobService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CronJobServiceImpl implements CronJobService {

    private final SongPlayLogRepository songPlayLogRepository;

    private final UserRepository userRepository;
    private final SongRepository songRepository;

    @Override
    @Transactional
    @Scheduled(cron = "0 0 0 1 * ?")
    public void updateSongPlayMetrics() {

        userRepository.updateLastMonthPlaysForAll(0L);
        userRepository.updateLastMonthListensForAll(0L);
        List<Object[]> userCount = songPlayLogRepository.findUniqueUsersWithCount();
        for(Object[] object:userCount){
            User user = (User) object[0];
            user.setTotalSongsListenedLastMonth((Long) object[1]);
            userRepository.save(user);
        }
        List<Object[]> songCount = songPlayLogRepository.findUniqueSongsWithCount();
        for(Object[] object:songCount){
            Song song = (Song) object[0];
            for(User artist : song.getArtists()){
                artist.setTotalSongsPlayedLastMonth((Long) (object[1]));
                userRepository.save(artist);
            }
            Long newListens = (Long) (object[1]);
            song.setListenCount(song.getListenCount()+newListens);
            songRepository.save(song);
        }
        songPlayLogRepository.deleteAll();
    }
}
