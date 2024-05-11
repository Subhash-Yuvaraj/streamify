package com.audiophile.streamify.service.impl;

import com.audiophile.streamify.dto.AddSongToPlaylistDTO;
import com.audiophile.streamify.dto.PlaylistDTO;
import com.audiophile.streamify.exception.PlaylistNotFoundException;
import com.audiophile.streamify.model.Playlist;
import com.audiophile.streamify.model.Song;
import com.audiophile.streamify.model.User;
import com.audiophile.streamify.repository.PlaylistRepository;
import com.audiophile.streamify.repository.SongRepository;
import com.audiophile.streamify.service.PlaylistService;
import com.audiophile.streamify.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final SongRepository songRepository;
    private final UserService userService;

    @Override
    public Playlist createPlaylist(PlaylistDTO playlistDTO) {
        Playlist playlist = new Playlist();
        playlist.setTitle(playlistDTO.getTitle());


        User creator = userService.getLogginUser();
        playlist.setCreator(creator);

        Set<Song> songs = new HashSet<>();
        for (Long songId : playlistDTO.getSongIds()) {
            Optional<Song> song = songRepository.findById(songId);
            if(song.isEmpty())
                throw new RuntimeException("Invalid song id");
            songs.add(song.get());
        }
        playlist.setSongs(songs);

        return playlistRepository.save(playlist);
    }

    @Override
    @Transactional
    public String addSongToPlaylist(Long playlistId, AddSongToPlaylistDTO addSongToPlaylistDTO){
        Optional<Playlist> playlistOptional = playlistRepository.findById(playlistId);
        if(playlistOptional.isEmpty())
            throw new RuntimeException("Invalid playlist id");
        Playlist playlist = playlistOptional.get();
        User loggedInUser = userService.getLogginUser();
        if(!playlist.getCreator().equals(loggedInUser))
            throw new RuntimeException("Invalid user trying to access");
        Optional<Song> optionalSong = songRepository.findById(addSongToPlaylistDTO.getSongId());
        if(optionalSong.isEmpty())
            throw new RuntimeException("Invalid songId");
        playlist.getSongs().add(optionalSong.get());
        playlistRepository.save(playlist);
        return "Successfully added";
    }

    @Override
    public Playlist getPlaylistById(Long playlistId) {
        User loggedinUser = userService.getLogginUser();
        Optional<Playlist> playlist = playlistRepository.findById(playlistId);
        if(playlist.isEmpty() || !playlist.get().getCreator().equals(loggedinUser))
            throw new PlaylistNotFoundException("Invalid playlist id provided");
        return playlist.get();
    }

}
