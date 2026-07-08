## Android Picker/Chooser & Permission ##

An Android example app demonstrating various file picker/chooser implementations and runtime permission handling. Pick image, file, photo, date/time, location, and audio recording in Android.

## Features

- **Gallery Picker** - Pick images from device gallery
- **Camera** - Capture photos using device camera
- **File Picker** - Select files from storage
- **Location** - Get current device location with map
- **DateTime Picker** - Select date and time
- **Audio Recording** - Record audio files
- **Notification** - Display notifications
- **File Download** - Download files with progress

## Permissions

| Permission | Purpose |
|------------|---------|
| `CAMERA` | Capture photos |
| `RECORD_AUDIO` | Record audio |
| `ACCESS_FINE_LOCATION` | Get precise location |
| `ACCESS_COARSE_LOCATION` | Get approximate location |
| `READ_EXTERNAL_STORAGE` | Read files (SDK ≤ 32) |
| `WRITE_EXTERNAL_STORAGE` | Write files (SDK ≤ 28) |
| `POST_NOTIFICATIONS` | Show notifications (SDK ≥ 33) |
| `INTERNET` | Network access |

## Requirements

- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 36
- **Java**: 17

## Build Variants

| Flavor | Application ID | Description |
|--------|----------------|-------------|
| `production` | `com.yoesuv.filepicker` | Default release build |
| `forTest` | `com.yoesuv.filepicker.test` | Test build variant |

## Project Structure

```
app/src/main/java/com/yoesuv/filepicker/
├── menu/
│   ├── main/          # Main activity & viewmodel
│   ├── gallery/       # Gallery image picker
│   ├── camera/        # Camera capture
│   ├── file/          # File picker
│   ├── location/      # Location with map
│   ├── datetime/      # Date/time picker
│   ├── record/        # Audio recording
│   ├── download/      # File download
│   └── notification/  # Notification demo
├── networks/          # Retrofit & Ktor API services
└── utils/             # File, permission & app helpers
```

## Screenshot

| ![](https://i.imgur.com/XrGCQMD.png) | ![](https://images2.imgbox.com/ff/0d/4sbHWzB7_o.png) | ![](https://i.imgur.com/boelaOE.png) | ![](https://i.imgur.com/XVpXWgg.png) |
|:------------------------------------:| :---: |:---:| :---: |
| ![](https://i.imgur.com/DrXTEEF.png) | ![](https://images2.imgbox.com/a6/e1/qj5HKxXl_o.png) | ![](https://i.imgur.com/sqMzGZC.png) | ![](https://images2.imgbox.com/14/cd/f9Ou8aWF_o.png) |
| ![](https://i.imgur.com/79OW4Ot.png) | ![](https://i.imgur.com/avNbA9O.png) | ![](https://i.imgur.com/GjFW9Du.png) | ![](https://i.imgur.com/BQxJmkt.png) |

## Libraries

| Library | Purpose |
|---------|---------|
| [Compressor](https://github.com/zetbaitsu/Compressor) | Image compression |
| [Glide](https://github.com/bumptech/glide) | Image loading |
| [OkHttp](https://github.com/square/okhttp) | HTTP client |
| [Retrofit](https://github.com/square/retrofit) | REST API client |
| [Ktor](https://github.com/ktorio/ktor) | HTTP client |
| [Play Services Location](https://developers.android.com/google/play-services) | Location services |

## Testing

Run instrumented tests:

```bash
./gradlew connectedForTestDebugAndroidTest
```

## How to Run

1. Clone the repository
2. Open in Android Studio
3. Sync Gradle
4. Run on device or emulator (API 24+)

## References

- [Stackoverflow - File Picker](https://stackoverflow.com/a/65763144/3559183)
- [Request location permissions](https://developer.android.com/training/location/permissions)
- [Data Storage](https://developer.android.com/training/data-storage)
- [MediaStore](https://developer.android.com/reference/android/provider/MediaStore)
- [File Info](https://developer.android.com/training/secure-file-sharing/retrieve-info)
- [Optimize Memory with Glide](https://proandroiddev.com/how-to-optimize-memory-consumption-when-using-glide-9ac984cfe70f)
- [UI Testing Permissions](https://alexzh.com/ui-testing-of-android-runtime-permissions/)
- [Media Recorder](https://developer.android.com/guide/topics/media/mediarecorder)
- [Storage Experience](https://android-developers.googleblog.com/2023/08/choosing-right-storage-experience.html)
