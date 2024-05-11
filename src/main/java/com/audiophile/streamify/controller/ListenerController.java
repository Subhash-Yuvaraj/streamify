package com.audiophile.streamify.controller;

import com.audiophile.streamify.dto.AddSongToPlaylistDTO;
import com.audiophile.streamify.dto.PlaylistDTO;
import com.audiophile.streamify.dto.SongResponseDTO;
import com.audiophile.streamify.exception.PlaylistNotFoundException;
import com.audiophile.streamify.model.Playlist;
import com.audiophile.streamify.service.PlaylistService;
import com.audiophile.streamify.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/listener")
@RequiredArgsConstructor
public class ListenerController {

    private final SongService songService;
    private final PlaylistService playlistService;
    @GetMapping()
    public ResponseEntity<String> greeting(){
        return ResponseEntity.ok("Hi Listener");
    }

    @GetMapping("/song/{songId}")
    public ResponseEntity<SongResponseDTO> playSong(@PathVariable Long songId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(songService.getSongDetailsAndAudio(songId));
    }

    @PostMapping("/create-playlist")
    public ResponseEntity<Playlist> createPlaylist(@RequestBody PlaylistDTO playlistDTO) {

        return ResponseEntity.status(HttpStatus.CREATED).body(playlistService.createPlaylist(playlistDTO));
    }

    @PutMapping("/playlist/{playlistId}/add-song")
    public ResponseEntity<String> addSongToPlaylist(@RequestBody AddSongToPlaylistDTO addSongToPlaylistDTO, @PathVariable Long playlistId){
        return ResponseEntity.status(HttpStatus.OK).body(playlistService.addSongToPlaylist(playlistId,addSongToPlaylistDTO));
    }

    @GetMapping("/playlist/{playlistId}")
    public ResponseEntity<Playlist> getPlaylistById(@PathVariable Long playlistId){
        try {
            Playlist playlist = playlistService.getPlaylistById(playlistId);
            return ResponseEntity.status(HttpStatus.OK).body(playlist);
        } catch (PlaylistNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }
}
