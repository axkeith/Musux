package com.example.musux.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.musux.download.YtMusicClient
import com.example.musux.download.YtMusicSearchResult
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(modifier: Modifier = Modifier) {
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    var searchResults by remember { mutableStateOf<List<YtMusicSearchResult>>(emptyList()) }
    val ytClient = remember { YtMusicClient() }
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = modifier) {
        SearchBar(
            query = query,
            onQueryChange = { query = it },
            onSearch = {
                active = false
                coroutineScope.launch {
                    searchResults = ytClient.searchMusic(it)
                }
            },
            active = active,
            onActiveChange = { active = it },
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            placeholder = { Text("Search YouTube Music...") },
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) }
        ) {
            // Content shown when search bar is active (e.g., search history)
        }

        LazyColumn {
            items(searchResults) { result ->
                ListItem(
                    headlineContent = { Text(result.title) },
                    supportingContent = { Text(result.artist) }
                )
            }
        }
    }
}
