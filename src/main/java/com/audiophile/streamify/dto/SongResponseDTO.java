package com.audiophile.streamify.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Data
@RequiredArgsConstructor
public class SongResponseDTO {
    private Long id;
    private String title;
    private Set<String> artistNames;
    private byte[] audio;
}
