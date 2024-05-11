package com.audiophile.streamify.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AddSongToPlaylistDTO {

    @NotNull
    private Long songId;

}
