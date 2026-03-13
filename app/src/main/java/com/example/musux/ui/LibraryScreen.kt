package com.example.musux.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.musux.MainViewModel

@Composable
fun LibraryScreen(viewModel: MainViewModel, modifier: Modifier = Modifier) {
    val songs by viewModel.allSongs.collectAsState(initial = emptyList())

    LazyColumn(modifier = modifier) {
        items(songs) { song ->
            ListItem(
                headlineContent = { Text(song.title) },
                supportingContent = { Text(song.artist) }
            )
        }
    }
}
