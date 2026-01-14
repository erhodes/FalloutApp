# Tech Context: FalloutApp

## Technologies Used

### Core Framework
| Technology | Version | Purpose |
|------------|---------|---------|
| Kotlin | 2.2.20 | Primary language |
| Kotlin Multiplatform | - | Cross-platform code sharing |
| Compose Multiplatform | 1.9.0 | Cross-platform UI framework |
| Gradle | - | Build system |
| Ktor | 3.0.0 | Embedded server (desktop) |

### UI & Design
| Library | Purpose |
|---------|---------|
| Compose Material3 | Material Design 3 components |
| Compose Material Icons | Icon resources |
| Material3 Adaptive | Adaptive layouts |
| Navigation Compose | Screen navigation |
| Compose Resources | Shared strings and drawables |

### Architecture & DI
| Library | Version | Purpose |
|---------|---------|---------|
| Koin Core | 4.1.1 | Dependency injection |
| AndroidX Lifecycle ViewModel | - | ViewModel support |
| AndroidX Lifecycle Runtime | - | Lifecycle-aware components |

### Data & Serialization
| Library | Version | Purpose |
|---------|---------|---------|
| kotlinx-serialization-json | 1.9.0 | JSON serialization |
| KStore | 1.0.0 | Cross-platform key-value storage |
| KStore Storage | 1.0.0 | Web-specific storage backend |
| KStore File | 1.0.0 | Desktop file storage backend |

## Development Setup

### Prerequisites
- **JDK 11+**: Required for Android compilation
- **Android Studio**: IDE with KMP support
- **Xcode** (macOS only): For iOS builds
- **Node.js**: For web/WASM builds

### Project Structure
```
FalloutApp/
├── composeApp/                    # Main multiplatform module
│   ├── src/
│   │   ├── commonMain/           # Shared code (all platforms)
│   │   │   ├── kotlin/com/erhodes/falloutapp/
│   │   │   │   ├── model/        # Domain models
│   │   │   │   ├── data/         # Data sources & serializers
│   │   │   │   ├── repository/   # Repositories
│   │   │   │   ├── presentation/ # ViewModels & UI states
│   │   │   │   ├── ui/           # Composable screens
│   │   │   │   ├── di/           # Koin setup
│   │   │   │   └── util/         # Utilities
│   │   │   └── composeResources/ # Shared resources
│   │   ├── androidMain/          # Android-specific code
│   │   ├── desktopMain/          # Desktop/JVM-specific code (server + UI)
│   │   ├── iosMain/              # iOS-specific code
│   │   ├── webMain/              # Web-specific code
│   │   ├── jsMain/               # JS target specifics
│   │   └── wasmJsMain/           # WASM target specifics
│   └── build.gradle.kts
├── iosApp/                        # iOS app entry point
├── gradle/                        # Gradle wrapper & version catalog
└── build.gradle.kts               # Root build file
```

### Build Commands

**Android:**
```bash
./gradlew :composeApp:assembleDebug
```

**Web (WASM):**
```bash
./gradlew :composeApp:wasmJsBrowserDevelopmentRun
```

**Web (JS):**
```bash
./gradlew :composeApp:jsBrowserDevelopmentRun
```

**Desktop (JVM):**
```bash
./gradlew :composeApp:run
```

**Desktop JAR:**
```bash
./gradlew :composeApp:desktopJar
```

**iOS:**
Open `iosApp/` in Xcode and run from there.

## Technical Constraints

### Platform Limitations
- **iOS builds**: Temporarily disabled (commented out in build.gradle.kts)
- **Storage**: Platform-specific implementations required (expect/actual pattern)
- **Logging**: Platform-specific AppLogger implementations

### Serialization Requirements
- All persisted classes must be `@Serializable`
- Custom serializers needed for polymorphic types (ItemTemplate, Requirement)
- Character class uses kotlinx.serialization

### Weight/Load Calculations
- Loadout limit: `strength + 4`
- Inventory limit: Fixed at 10
- Stackable items: load = count * baseLoad

### Skill Constraints
- 12 skills total (ordinal 0-11)
- Max skill value: `5 + level/2`
- Initial skill value: 2

## Dependencies

### Version Catalog (gradle/libs.versions.toml)
Referenced via `libs.` in build files:
- `libs.plugins.kotlinMultiplatform`
- `libs.plugins.androidApplication`
- `libs.plugins.composeMultiplatform`
- `libs.plugins.composeCompiler`
- `libs.androidx.activity.compose`
- `libs.androidx.lifecycle.viewmodelCompose`
- `libs.androidx.lifecycle.runtimeCompose`
- `libs.navigation.compose`
- `libs.kstore`
- `libs.kstore.storage`
- `libs.kstore.file`
- `libs.ktor.server.core`
- `libs.ktor.server.netty`
- `libs.ktor.server.content.negotiation`
- `libs.ktor.serialization.kotlinx.json`
- `libs.kotlin.test`

### Android Configuration
- **compileSdk**: From version catalog
- **minSdk**: From version catalog
- **targetSdk**: From version catalog
- **applicationId**: `com.erhodes.falloutapp`

## Tool Usage Patterns

### Koin Injection
ViewModels inject dependencies via `KoinComponent`:
```kotlin
class CharacterViewModel : ViewModel(), KoinComponent {
    private val repo: CharacterRepository by inject()
}
```

### StateFlow Pattern
```kotlin
private val _state = MutableStateFlow(initialState)
val state = _state.asStateFlow()

fun updateState() {
    scope.launch {
        _state.update { newState }
    }
}
```

### Platform Expect/Actual
```kotlin
// commonMain
expect fun getStorage(): Storage

// androidMain
actual fun getStorage(): Storage = AndroidStorage()

// webMain
actual fun getStorage(): Storage = WebStorage()
```

### Navigation
```kotlin
navController.navigate(FalloutScreen.CharacterScreen.name)
navController.popBackStack()
navController.popBackStack(route = FalloutScreen.CharacterList.name, false)
```
