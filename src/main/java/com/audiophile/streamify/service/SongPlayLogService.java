package com.audiophile.streamify.service;

import com.audiophile.streamify.model.Song;
import com.audiophile.streamify.model.SongPlayLog;

public interface SongPlayLogService {
    SongPlayLog addLog(Song song);
}
