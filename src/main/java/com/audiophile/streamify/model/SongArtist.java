package com.audiophile.streamify.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import jakarta.persistence.*;
@Entity
@Table(name = "song_artists")
@Data
@RequiredArgsConstructor
public class SongArtist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "song_id")
    private Song song;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    private User artist;
}