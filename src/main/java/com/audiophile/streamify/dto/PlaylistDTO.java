package com.audiophile.streamify.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Data
@RequiredArgsConstructor
public class PlaylistDTO {
    private String title;
    private Set<Long> songIds;
}
