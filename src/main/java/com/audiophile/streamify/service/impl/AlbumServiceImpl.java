package com.audiophile.streamify.service.impl;

import com.audiophile.streamify.service.AlbumService;
import com.audiophile.streamify.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.audiophile.streamify.dto.AlbumDTO;
import com.audiophile.streamify.model.Album;
import com.audiophile.streamify.model.User;
import com.audiophile.streamify.repository.AlbumRepository;
import com.audiophile.streamify.repository.UserRepository;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AlbumServiceImpl implements AlbumService {

    private final AlbumRepository albumRepository;

    private final UserRepository userRepository;

    private final Environment environment;
    private final UserService userService;

    @Override
    public Album addAlbum(AlbumDTO albumDTO) {
        Album album = new Album();
        album.setTitle(albumDTO.getTitle());
        album.setCoverImage(saveCoverImage(albumDTO.getCoverImage()));


        User loggedInUser = userService.getLogginUser();
        Set<User> initialUser = new HashSet<>();
        initialUser.add(loggedInUser);
        album.setArtists(initialUser);
        
        if (albumDTO.getArtistIds() != null) {
            for (Long artistId : albumDTO.getArtistIds()) {
                if (!artistId.equals(loggedInUser.getId())) {
                    Optional<User> artist = userRepository.findById(artistId);
                    if(artist.isEmpty())
                        throw new RuntimeException("Invalid user");
                    album.getArtists().add(artist.get());
                }
            }
        }

        return albumRepository.save(album);
    }

    private String saveCoverImage(MultipartFile coverImage) {
        String albumFolder = environment.getProperty("file.directory")+"/AlbumCover";
        String fileName = UUID.randomUUID()+"-"+coverImage.getOriginalFilename();
        String filePath = albumFolder+ File.separator+fileName;
        File destinationFile = new File(filePath);
        try{
            coverImage.transferTo(destinationFile);
            return filePath;
        }
        catch (IndexOutOfBoundsException | IOException e){
            throw  new RuntimeException("Failed to save the file", e);
        }
    }
}
