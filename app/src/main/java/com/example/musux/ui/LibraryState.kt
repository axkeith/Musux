package com.example.musux.ui

enum class ViewType {
    LIST,
    GRID,
    DETAILS
}

enum class SortOption {
    TITLE,
    ARTIST,
    ALBUM,
    DATE_ADDED,
    DURATION
}

enum class SortOrder {
    ASCENDING,
    DESCENDING
}

enum class LibraryCategory {
    SONGS,
    ARTISTS,
    ALBUMS
}

data class LibraryState(
    val category: LibraryCategory = LibraryCategory.SONGS,
    val viewType: ViewType = ViewType.LIST,
    val sortOption: SortOption = SortOption.TITLE,
    val sortOrder: SortOrder = SortOrder.ASCENDING
)
