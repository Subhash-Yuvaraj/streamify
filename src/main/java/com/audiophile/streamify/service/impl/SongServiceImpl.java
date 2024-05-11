package com.audiophile.streamify.service.impl;

import com.audiophile.streamify.dto.SongDTO;
import com.audiophile.streamify.dto.SongResponseDTO;
import com.audiophile.streamify.model.Album;
import com.audiophile.streamify.model.Song;
import com.audiophile.streamify.model.User;
import com.audiophile.streamify.repository.AlbumRepository;
import com.audiophile.streamify.repository.SongRepository;
import com.audiophile.streamify.repository.UserRepository;
import com.audiophile.streamify.service.SongPlayLogService;
import com.audiophile.streamify.service.SongService;
import com.audiophile.streamify.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SongServiceImpl implements SongService {

    private final SongRepository songRepository;
    private final AlbumRepository albumRepository;
    private final Environment environment;
    private final UserRepository userRepository;
    private final SongPlayLogService songPlayLogService;
    private final UserService userService;

    @Override
    public Song createSong(SongDTO songDTO) {
        Song song = new Song();
        song.setTitle(songDTO.getTitle());
        Optional<Album> optionalAlbum = albumRepository.findById(songDTO.getAlbumId());
        if(optionalAlbum.isEmpty())
            throw new RuntimeException("Invalid album");
        Album album = optionalAlbum.get();
        song.setAlbum(album);
        User loggedInUser = userService.getLogginUser();

        song.setListenCount(0L);

        Set<User> artists = new HashSet<>();
        artists.add(loggedInUser);
        for(Long userId:songDTO.getArtistIds()){
            Optional<User> artist = userRepository.findById(userId);
            if(artist.isEmpty() || artist.get().getRole().name().equals("LISTENER"))
                throw new RuntimeException("Invalid artist id provided");
            artists.add(artist.get());
        }
        artists.addAll(album.getArtists());
        song.setArtists(artists);
        song.setFilePath(saveSong(songDTO.getSongFile()));
        return songRepository.save(song);
    }
    private String saveSong(MultipartFile song) {
        String songFolder = environment.getProperty("file.directory")+"/Songs";
        String fileName = UUID.randomUUID()+"-"+song.getOriginalFilename();
        String filePath = songFolder+ File.separator+fileName;
        File destinationFile = new File(filePath);
        try{
            song.transferTo(destinationFile);
            return filePath;
        }
        catch (IndexOutOfBoundsException | IOException e){
            throw  new RuntimeException("Failed to save the file", e);
        }
    }

    @Override
    public SongResponseDTO getSongDetailsAndAudio(Long songId) {
        Optional<Song> song = songRepository.findById(songId);
        if(song.isEmpty())
            throw new RuntimeException("The song is not found");
        SongResponseDTO responseDTO = new SongResponseDTO();
        responseDTO.setId(song.get().getId());
        responseDTO.setTitle(song.get().getTitle());
        responseDTO.setAudio(getAudioFileBytes(song.get().getFilePath()));
        Set<String> artists = new HashSet<>(userRepository.getArtistNames(songId));

        responseDTO.setArtistNames(artists);
        songPlayLogService.addLog(song.get());
        return responseDTO;
    }


    private byte[] getAudioFileBytes(String filePath) {
        try {
            return Files.readAllBytes(Paths.get(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Error reading audio file");
        }
    }
}
