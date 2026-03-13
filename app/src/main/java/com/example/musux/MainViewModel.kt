package com.example.musux

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.musux.data.AppDatabase
import com.example.musux.data.MediaScanner
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getDatabase(application)
    private val songDao = database.songDao()
    private val mediaScanner = MediaScanner(application, songDao)

    val allSongs = songDao.getAllSongs()

    fun scanMedia() {
        viewModelScope.launch {
            mediaScanner.scanLocalMedia()
        }
    }
}
