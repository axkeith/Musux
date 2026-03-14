package com.example.musux

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.musux.data.AppDatabase
import com.example.musux.data.MediaScanner
import com.example.musux.data.Song
import com.example.musux.ui.LibraryCategory
import com.example.musux.ui.LibraryState
import com.example.musux.ui.SortOption
import com.example.musux.ui.SortOrder
import com.example.musux.ui.ViewType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getDatabase(application)
    private val songDao = database.songDao()
    private val mediaScanner = MediaScanner(application, songDao)

    val allSongs = songDao.getAllSongs()

    private val _libraryState = MutableStateFlow(LibraryState())
    val libraryState: StateFlow<LibraryState> = _libraryState.asStateFlow()

    // Sorted and filtered list based on libraryState
    val sortedSongs: StateFlow<List<Song>> = combine(allSongs, _libraryState) { songs, state ->
        var result = songs

        // Only keeping SONGS category implementation for the list in this flow for now
        // Advanced grouping for Artists/Albums can be handled in UI or separate flows

        when (state.sortOption) {
            SortOption.TITLE -> {
                result = if (state.sortOrder == SortOrder.ASCENDING) result.sortedBy { it.title }
                         else result.sortedByDescending { it.title }
            }
            SortOption.ARTIST -> {
                result = if (state.sortOrder == SortOrder.ASCENDING) result.sortedBy { it.artist }
                         else result.sortedByDescending { it.artist }
            }
            SortOption.ALBUM -> {
                result = if (state.sortOrder == SortOrder.ASCENDING) result.sortedBy { it.album }
                         else result.sortedByDescending { it.album }
            }
            SortOption.DATE_ADDED -> {
                result = if (state.sortOrder == SortOrder.ASCENDING) result.sortedBy { it.dateAdded }
                         else result.sortedByDescending { it.dateAdded }
            }
            SortOption.DURATION -> {
                result = if (state.sortOrder == SortOrder.ASCENDING) result.sortedBy { it.duration }
                         else result.sortedByDescending { it.duration }
            }
        }
        result
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    // Update library state
    fun setCategory(category: LibraryCategory) {
        _libraryState.value = _libraryState.value.copy(category = category)
    }

    fun setViewType(viewType: ViewType) {
        _libraryState.value = _libraryState.value.copy(viewType = viewType)
    }

    fun setSortOption(sortOption: SortOption) {
        _libraryState.value = _libraryState.value.copy(sortOption = sortOption)
    }

    fun toggleSortOrder() {
        val current = _libraryState.value.sortOrder
        val newOrder = if (current == SortOrder.ASCENDING) SortOrder.DESCENDING else SortOrder.ASCENDING
        _libraryState.value = _libraryState.value.copy(sortOrder = newOrder)
    }

    fun scanMedia() {
        viewModelScope.launch {
            mediaScanner.scanLocalMedia()
        }
    }
}
