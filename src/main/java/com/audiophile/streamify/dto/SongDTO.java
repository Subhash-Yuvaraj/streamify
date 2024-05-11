package com.audiophile.streamify.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Data
@RequiredArgsConstructor
public class SongDTO {
    private String title;

    private Long albumId;

    private MultipartFile songFile;

    private Set<Long> artistIds;
}
