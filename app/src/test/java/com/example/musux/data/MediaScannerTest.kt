package com.example.musux.data

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
class MediaScannerTest {

    @Mock
    private lateinit var context: Context

    @Mock
    private lateinit var contentResolver: ContentResolver

    @Mock
    private lateinit var cursor: Cursor

    @Mock
    private lateinit var songDao: SongDao

    private lateinit var mediaScanner: MediaScanner

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        `when`(context.contentResolver).thenReturn(contentResolver)
        mediaScanner = MediaScanner(context, songDao)
    }

    @Test
    fun `scanLocalMedia should parse cursor and insert songs`() = runTest {
        `when`(contentResolver.query(any(), any(), anyOrNull(), anyOrNull(), anyOrNull())).thenReturn(cursor)
        `when`(cursor.count).thenReturn(1)
        `when`(cursor.moveToNext()).thenReturn(true, false)

        // Mock column indices
        `when`(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)).thenReturn(0)
        `when`(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)).thenReturn(1)
        `when`(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)).thenReturn(2)
        `when`(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)).thenReturn(3)
        `when`(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)).thenReturn(4)
        `when`(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)).thenReturn(5)
        `when`(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)).thenReturn(6)
        `when`(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_ADDED)).thenReturn(7)

        // Mock row data
        `when`(cursor.getLong(0)).thenReturn(1L)
        `when`(cursor.getString(1)).thenReturn("Test Title")
        `when`(cursor.getString(2)).thenReturn("Test Artist")
        `when`(cursor.getString(3)).thenReturn("Test Album")
        `when`(cursor.getLong(4)).thenReturn(120000L)
        `when`(cursor.getString(5)).thenReturn("/path/to/song.mp3")
        `when`(cursor.getLong(6)).thenReturn(100L)
        `when`(cursor.getLong(7)).thenReturn(1000L)

        mediaScanner.scanLocalMedia()

        verify(songDao).deleteAllSongs()
        verify(songDao).insertSongs(any())
    }
}
