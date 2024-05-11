package com.audiophile.streamify.controller;

import com.audiophile.streamify.dto.AlbumDTO;
import com.audiophile.streamify.dto.SongDTO;
import com.audiophile.streamify.model.Album;
import com.audiophile.streamify.model.Song;
import com.audiophile.streamify.service.AlbumService;
import com.audiophile.streamify.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/artist")
@RequiredArgsConstructor
public class ArtistController {

    private final AlbumService albumService;
    private final SongService songService;
    @GetMapping
    public ResponseEntity<String> greeting(){
        return ResponseEntity.ok("Hi Artist");
    }

    @PostMapping("/add-album")
    public ResponseEntity<Album> addAlbum(@RequestParam("coverImage")MultipartFile coverImage, @RequestParam("title") String title,@RequestParam("artistIds") List<Long> artistIds) {
        AlbumDTO albumDTO = new AlbumDTO();
        Set<Long> artistSet = new HashSet<>(artistIds);
        albumDTO.setArtistIds(artistSet);
        albumDTO.setTitle(title);
        albumDTO.setCoverImage(coverImage);
        Album album = albumService.addAlbum(albumDTO);
        return new ResponseEntity<>(album, HttpStatus.CREATED);
    }

    @PostMapping("/add-song")
    public ResponseEntity<Song> addSong(@RequestParam("title") String title, @RequestParam("albumId") Long albumId, @RequestParam("songFile") MultipartFile songFile, @RequestParam("artistIds") List<Long> artistsIds){
        SongDTO songDTO = new SongDTO();
        songDTO.setTitle(title);
        songDTO.setSongFile(songFile);
        songDTO.setAlbumId(albumId);
        songDTO.setArtistIds(new HashSet<>(artistsIds));
        Song song = songService.createSong(songDTO);
        return new ResponseEntity<>(song,HttpStatus.CREATED);
    }
}
