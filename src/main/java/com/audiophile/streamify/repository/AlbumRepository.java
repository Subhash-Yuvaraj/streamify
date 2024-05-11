package com.audiophile.streamify.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.audiophile.streamify.model.Album;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

}
