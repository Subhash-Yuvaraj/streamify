package com.audiophile.streamify.service;

import com.audiophile.streamify.dto.AddSongToPlaylistDTO;
import com.audiophile.streamify.dto.PlaylistDTO;
import com.audiophile.streamify.model.Playlist;
import jakarta.transaction.Transactional;

public interface PlaylistService {
    Playlist createPlaylist(PlaylistDTO playlistDTO);

    String addSongToPlaylist(Long playlistId, AddSongToPlaylistDTO addSongToPlaylistDTO);

    Playlist getPlaylistById(Long playlistId);
}
