package com.audiophile.streamify.service;

import com.audiophile.streamify.dto.SongDTO;
import com.audiophile.streamify.dto.SongResponseDTO;
import com.audiophile.streamify.model.Song;

public interface SongService {
    Song createSong(SongDTO songDTO);
    SongResponseDTO getSongDetailsAndAudio(Long songId);
}
