package com.example.musux.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material.icons.filled.ViewAgenda
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.musux.MainViewModel

@Composable
fun LibraryScreen(viewModel: MainViewModel, modifier: Modifier = Modifier) {
    val state by viewModel.libraryState.collectAsState()
    val songs by viewModel.sortedSongs.collectAsState()

    // In future logic, this could provide distinct lists for artists/albums
    val categories = LibraryCategory.values()

    Column(modifier = modifier.fillMaxSize()) {
        ScrollableTabRow(
            selectedTabIndex = state.category.ordinal,
            edgePadding = 8.dp
        ) {
            categories.forEach { category ->
                Tab(
                    selected = state.category == category,
                    onClick = { viewModel.setCategory(category) },
                    text = { Text(category.name) }
                )
            }
        }

        // Control Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("${songs.size} items")

            Row {
                SortControl(state, viewModel)
                ViewControl(state, viewModel)
            }
        }

        // Display Area based on ViewType
        when (state.viewType) {
            ViewType.LIST -> LibraryList(songs)
            ViewType.GRID -> LibraryGrid(songs)
            ViewType.DETAILS -> LibraryDetails(songs)
        }
    }
}

@Composable
fun SortControl(state: LibraryState, viewModel: MainViewModel) {
    var sortMenuExpanded by remember { mutableStateOf(false) }

    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = { viewModel.toggleSortOrder() }) {
            Icon(
                imageVector = if (state.sortOrder == SortOrder.ASCENDING) Icons.Filled.ArrowUpward else Icons.Filled.ArrowDownward,
                contentDescription = "Toggle Sort Order"
            )
        }
        Box {
            IconButton(onClick = { sortMenuExpanded = true }) {
                Icon(Icons.Filled.Sort, contentDescription = "Sort By")
            }
            DropdownMenu(
                expanded = sortMenuExpanded,
                onDismissRequest = { sortMenuExpanded = false }
            ) {
                SortOption.values().forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option.name) },
                        onClick = {
                            viewModel.setSortOption(option)
                            sortMenuExpanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ViewControl(state: LibraryState, viewModel: MainViewModel) {
    IconButton(
        onClick = {
            val nextView = when (state.viewType) {
                ViewType.LIST -> ViewType.GRID
                ViewType.GRID -> ViewType.DETAILS
                ViewType.DETAILS -> ViewType.LIST
            }
            viewModel.setViewType(nextView)
        }
    ) {
        val icon = when (state.viewType) {
            ViewType.LIST -> Icons.Filled.List
            ViewType.GRID -> Icons.Filled.GridView
            ViewType.DETAILS -> Icons.Filled.ViewAgenda
        }
        Icon(icon, contentDescription = "Change View")
    }
}
