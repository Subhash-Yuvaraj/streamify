package com.audiophile.streamify.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Data
@RequiredArgsConstructor
public class AlbumDTO {
    private String title;
    private Set<Long> artistIds;
    private MultipartFile coverImage;
}
