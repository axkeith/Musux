# Musux

Musux is an advanced, heavily optimized, fast, local-first Android music player. It is designed to be a completely offline audio experience with the singular exception of a cloud storage sync feature to keep your music library backed up and accessible.

## Architecture & Tech Stack

Musux is built with modern Android development standards:

*   **Language:** Kotlin
*   **UI:** Jetpack Compose (Material 3 Expressive)
*   **Dependency Injection:** Dagger Hilt
*   **Local Database:** Room
*   **Networking (Cloud Sync):** Retrofit
*   **Asynchrony:** Coroutines & Flow
*   **Audio Playback:** Media3 ExoPlayer
*   **Background Processing:** WorkManager

### Package Structure
The app's codebase is logically organized into the following layers:
*   `ui/` - Compose screens, ViewModels, and UI-specific state management.
*   `data/` - Repositories, Room DAOs, Data Sources, and Retrofit services.
*   `player/` - Media3 ExoPlayer integration, media session handling, and playback logic.
*   `di/` - Dagger Hilt modules for dependency provision.
*   `download/` - WorkManager tasks and logic for cloud storage sync and file management.

## Roadmap & Advanced Features

Inspired by industry-leading offline players like Musicolet, Musux aims to deliver a power-user experience. Below is the comprehensive list of planned and active features:

### Core Playback
*   **Multiple Queues:** Manage and switch between multiple independent queues (e.g., one for podcasts, one for music).
*   **Gapless Playback:** Seamless transitions between tracks.
*   **Crossfade:** Smooth blending of tracks when changing songs.
*   **Pitch & Tempo Controls:** Adjust playback speed and pitch independently.
*   **ReplayGain Support:** Automatic volume normalization across different tracks.

### Library & Organization
*   **Folder Browsing:** Navigate and play music directly from the device's directory structure.
*   **Advanced Tag Editor:** Edit ID3 tags, album art, lyrics, and metadata for individual or multiple files.
*   **MusicBrainz Auto-tagging:** Automatically fetch missing metadata and album art from the MusicBrainz database.
*   **Smart Playlists:** Automatically generated playlists (e.g., Most Played, Recently Added, Favorites, Never Played).
*   **Advanced Sorting & Filtering:** Filter by genre, year, composer, duration, or file format.

### Audio Tuning
*   **Advanced Equalizer:** 10+ band equalizer with custom presets.
*   **Audio Effects:** Bass Boost, Virtualizer/Surround Sound, and Reverb effects.
*   **Audio Focus Management:** Graceful handling of interruptions (calls, notifications).

### User Interface & Experience
*   **Material 3 Expressive UI:** Beautiful, dynamic UI that adapts to album art and system colors (Monet).
*   **Embedded Lyrics Support:** Display embedded synchronized (LRC) and unsynchronized lyrics.
*   **Advanced Sleep Timer:** Configurable timer with options to fade out volume, stop after the current song, or stop after a specific number of tracks.
*   **Customizable Widgets:** Highly customizable home screen widgets.
*   **Lockscreen & Notification Controls:** Rich media controls integrated with Android's MediaSession.

### Connectivity & Sync
*   **Cloud Storage Sync:** The *only* online feature. Sync your local library, playlists, and settings with cloud providers (Google Drive, Dropbox, OneDrive) using WorkManager and Retrofit.
*   **Android Auto Support:** Full integration for safe driving.
*   **Headset/Bluetooth Controls:** Auto-resume on connect, auto-pause on disconnect, and customizable button behaviors.

## Building the Project

You can compile and build the project using standard Gradle commands.

To build an APK without running tests or linting (for maximum speed):
```bash
./gradlew build -x test -x lint
```

To assemble a debug APK:
```bash
./gradlew assembleDebug
```

## Contributing

We welcome contributions to make Musux the best local music player on Android.

1. Fork the repository.
2. Create your feature branch (`git checkout -b feature/AmazingFeature`).
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`).
4. Push to the branch (`git push origin feature/AmazingFeature`).
5. Open a Pull Request.

Please ensure your code adheres to the project's architecture and is heavily optimized for speed.

## License

This project is licensed under the appropriate open-source license. See the `LICENSE` file for details.