package com.audiophile.streamify.repository;

import com.audiophile.streamify.model.PlaylistSong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PlaylistSongRepository extends JpaRepository<PlaylistSong, Long> {
    @Query(nativeQuery = true, value = "insert into playlist_songs(playlist_id, song_id) values (:=playlistId, :=songId)")
    void insertSongToPlaylist(@Param("playlistId") Long playlistId, @Param("songId") Long songId);
}
