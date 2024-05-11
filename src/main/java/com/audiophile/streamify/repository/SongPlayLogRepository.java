package com.audiophile.streamify.repository;

import com.audiophile.streamify.model.SongPlayLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SongPlayLogRepository extends JpaRepository<SongPlayLog, Long> {
    @Query("SELECT spl.user, COUNT(spl.user) FROM SongPlayLog spl GROUP BY spl.user")
    List<Object[]> findUniqueUsersWithCount();
    @Query("SELECT spl.song, COUNT(spl.song) FROM SongPlayLog spl GROUP BY spl.song")
    List<Object[]> findUniqueSongsWithCount();
}
