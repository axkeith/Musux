package com.example.musux.download

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

data class YtMusicSearchResult(
    val id: String,
    val title: String,
    val artist: String,
    val album: String,
    val thumbnailUrl: String
)

class YtMusicClient {
    // Mock implementation for searching YouTube Music
    suspend fun searchMusic(query: String): List<YtMusicSearchResult> = withContext(Dispatchers.IO) {
        delay(1000) // Simulate network delay
        listOf(
            YtMusicSearchResult(
                id = "mock_id_1",
                title = "$query - Song 1",
                artist = "Artist A",
                album = "Album X",
                thumbnailUrl = "https://example.com/thumb1.jpg"
            ),
            YtMusicSearchResult(
                id = "mock_id_2",
                title = "$query - Song 2",
                artist = "Artist B",
                album = "Album Y",
                thumbnailUrl = "https://example.com/thumb2.jpg"
            )
        )
    }

    suspend fun downloadSong(id: String) {
        withContext(Dispatchers.IO) {
            delay(2000) // Simulate downloading process
            // Stub for downloader with metadata and lyrics tagging
        }
    }
}
