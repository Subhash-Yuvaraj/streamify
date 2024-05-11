package com.audiophile.streamify.service;

import com.audiophile.streamify.dto.AlbumDTO;
import com.audiophile.streamify.model.Album;

public interface AlbumService {

    Album addAlbum(AlbumDTO albumDTO);
}
