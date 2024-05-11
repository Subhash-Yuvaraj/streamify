package com.audiophile.streamify.repository;

import com.audiophile.streamify.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    @Query("UPDATE User u SET u.totalSongsPlayedLastMonth =:newValue")
    @Modifying
    @Transactional
    void updateLastMonthPlaysForAll(@Param("newValue") Long newValue);

    @Query("UPDATE User u SET u.totalSongsListenedLastMonth =:newValue")
    @Modifying
    @Transactional
    void updateLastMonthListensForAll(@Param("newValue") Long newValue);

    @Query(nativeQuery = true, value = "select u.name from users u join song_artists sa on sa.artist_id = u.id where sa.song_id =:songId")
    List<String> getArtistNames(@Param("songId") Long songId);
}
