# 🎮 Logo Quiz Arena

An arcade-styled Android brand trivia and logo guessing game built with **Kotlin**, **Jetpack Compose (Material 3)**, **Room Database**, and **Unity LevelPlay Ads Mediation**. Features real-time CDN/GitHub logo synchronization, progressive stage unlocking, and interactive letter tile mechanics.

---

## 🌟 Key Features

### 🏆 8 Diverse Pack Categories
- **🏎️ Automotive**: Car brands, motorcycles, and tuning marques.
- **🍔 Food & Drink**: Fast food, beverages, snacks, and global chains.
- **💻 Tech Giants**: Software, hardware, and mobile operating systems.
- **👟 Fashion**: Sportswear, luxury fashion, and street apparel.
- **🎬 Movies & Media**: Streaming platforms, cinema studios, and entertainment.
- **💳 Finance**: Global banks, card networks, and payment processors.
- **⚽ Sports**: Leagues, football clubs, and athletic gear.
- **🛒 Retail**: Supermarket chains and global e-commerce portals.

---

### ⚡ Real-Time GitHub & CDN Logo Sync
- **Live Sync Engine**: Dynamically fetches and merges new brand logos added directly to the [`pandasuryanarayan/logoquiz`](https://github.com/pandasuryanarayan/logoquiz) repository on GitHub.
- **Resilient Fallback Pipeline**:
  1. *Primary*: Real-time GitHub Recursive Git Tree API (`api.github.com/repos/.../git/trees/main?recursive=1`).
  2. *Secondary*: GitHub Per-Folder Contents API.
  3. *Tertiary*: jsDelivr CDN Data API (`data.jsdelivr.com`).
- **Cache Persistence**: Dynamically discovered levels are reconciled with the local Room Database and persisted in `SharedPreferences` for offline play.

---

### 🕹️ Gameplay & Mechanics
- **Scrambled Letter Bank**: Tap tiles to fill target answer slots; tap filled slots to return tiles.
- **Interactive Hints**:
  - **Free Hint**: Reveal a correct letter once per level.
  - **Remove Distractors**: Eliminate 3 incorrect letters via Rewarded Video Ad.
  - **Reveal Letter**: Uncover the next correct letter using 40 in-game coins or watching a Rewarded Ad.
  - **Coin Rewards**: Earn +50 coins or double level completion rewards by watching Ads.
- **Stage Progression & Gating**:
  - Levels 1–5 are unlocked sequentially upon completing the previous level.
  - Levels 6–10 are Rewarded-Ad gated, requiring a brief watch-to-unlock once eligible.
- **Arcade Controls**: Shuffle tile bank, clear all uncommitted slots, and enjoy error-shake feedback animations.

---

### 🛡️ Dual Play Modes
- **User Mode**: Official player journey with coin rewards, star ratings, lock gates, and persistent progression.
- **Admin Testing Mode**: Isolated sandbox mode where all level packs, stages, and letter reveal hints are fully unlocked for developer verification without modifying player database stats or coin balances.

---

### 📺 Unity LevelPlay Mediation
- **LevelPlay Ads Integration**: Built-in Unity LevelPlay Rewarded Video Ads SDK (`App Key: 28098d405`).
- **Graceful Error Handling**: Includes custom loading modals, error fallbacks, and test simulation dialogs for seamless user experience during ad loading or network hiccups.

---

## 🏗️ Technical Architecture

| Layer | Technologies / Libraries |
| :--- | :--- |
| **Language** | Kotlin 1.9+ |
| **UI Framework** | Jetpack Compose, Material Design 3, Compose Animations |
| **Architecture** | MVVM (Model-View-ViewModel), Coroutines, StateFlow |
| **Database** | Room Database (KSP) (`LevelProgressEntity`, `UserProfileEntity`) |
| **Networking** | Java HttpURLConnection, `kotlinx.coroutines` |
| **Image Loading** | Coil (`io.coil-kt:coil-compose`) |
| **Ad Mediation** | Unity LevelPlay Ads SDK (`com.unity3d.ads-mediation`) |

---

## 📁 Repository Structure

```
├── app/
│   ├── build.gradle.kts           # Module build configuration & dependencies
│   └── src/main/
│       ├── AndroidManifest.xml    # Permissions, hardware features, and Activity config
│       ├── java/com/example/
│       │   ├── MainActivity.kt    # Main Compose entrypoint & root navigation controller
│       │   ├── ads/
│       │   │   └── LevelPlayAdsManager.kt  # LevelPlay SDK initialization & ad callbacks
│       │   ├── data/
│       │   │   ├── QuizModels.kt           # Data classes & Room Entities
│       │   │   ├── QuizDao.kt              # Room DAO interfaces
│       │   │   ├── QuizDatabase.kt         # Room Database builder
│       │   │   ├── QuizPackData.kt         # Bundled level packs & stage lock logic
│       │   │   ├── QuizRepository.kt       # Unified data repository & sync logic
│       │   │   └── RemoteLogoSyncManager.kt# GitHub & jsDelivr real-time sync engine
│       │   ├── ui/
│       │   │   ├── components/            # Reusable Compose UI components & modals
│       │   │   ├── screens/               # ModeSelection, PackSelection, LevelGrid, QuizPlay
│       │   │   ├── theme/                 # Arcade color palette, typography, shapes
│       │   │   └── viewmodel/             # QuizViewModel (reactive game state engine)
│       └── res/                       # Vectors, strings, app icons, and drawables
├── build.gradle.kts               # Top-level build script
├── metadata.json                  # AI Studio platform identification file
└── README.md                      # Project documentation
```

---

## 🛠️ Building & Installation

### Requirements
- Android Studio Jellyfish / Ladybug or Gradle CLI 8.x
- JDK 17
- Android SDK (Compile SDK 34, Min SDK 24)

### Building the Release APK via CLI
To build a signed release APK:
```bash
gradle :app:assembleRelease
```
The compiled APK will be located at:
`app/build/outputs/apk/release/app-universal-release.apk`

---

## 📄 License
Created with **Google AI Studio Build**. Distributed under open project terms for demonstration and personal use.
