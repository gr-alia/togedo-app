# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Kotlin Multiplatform (KMP) application targeting multiple platforms with a shared codebase:
- **Android** (minSdk 21, targetSdk 35)
- **iOS** (arm64, x64, simulator)
- **Desktop** (JVM)
- **Web** (Wasm - Alpha)

The application uses Compose Multiplatform for shared UI across all platforms.

## Build and Run Commands

### Android
- **Run**: Open project in Android Studio and run the imported android run configuration
- **Build APK**: `./gradlew :composeApp:assembleDebug`
  - Output: `composeApp/build/outputs/apk/debug/composeApp-debug.apk`
- **Run UI tests**: `./gradlew :composeApp:connectedDebugAndroidTest`

### Desktop (JVM)
- **Run**: `./gradlew :composeApp:run`
- **Run with hot reload**: `./gradlew :composeApp:hotRunJvm`
- **Run UI tests**: `./gradlew :composeApp:jvmTest`

### iOS
- **Run**: Open `iosApp/iosApp.xcproject` in Xcode and run standard configuration
  - Alternatively, use the Kotlin Multiplatform Mobile plugin for Android Studio
- **Run simulator tests**: `./gradlew :composeApp:iosSimulatorArm64Test`

### Web (Wasm)
- **Run**: `./gradlew :composeApp:wasmJsBrowserDevelopmentRun --continue`
- **Run tests**: `./gradlew :composeApp:wasmJsBrowserTest`

### Prerequisites
- JDK 17 or higher required
- Add `local.properties` file to project root with path to Android SDK
- Run [KDoctor](https://github.com/Kotlin/kdoctor) to verify system setup

## Key Technologies and Libraries

- **Dependency Injection**: Koin (v4.1.1) - used across all modules
- **Networking**: Ktor (v3.3.2) - for shared networking code
  - Platform-specific engines: OkHttp (Android/JVM), Darwin (iOS)
- **Navigation**: Voyager (v1.1.0-beta03) - Navigator and TabNavigator for screen navigation
- **State Holder**: Voyager (v1.1.0-beta03) - Voyager ScreenModel (alternative of Android's ViewModel)
- **Serialization**: kotlinx.serialization (v1.9.0)
- **Date/Time**: kotlinx-datetime (v0.7.1) - multiplatform date/time handling
- **State Storage**: KStore (v1.0.0) - multiplatform key-value storage
- **Logging**: Kermit (v2.0.8)
- **Design System**: Custom design system with Lumo UI plugin (v1.2.5)
- **Icons**: Compose Icons - Feather (v1.1.1)

## Code Architecture

### Navigation Structure

The app uses a two-level navigation architecture:

```
App.kt
└── AppNavigator.kt
    ├── OnboardingFlow.kt (first launch, onboarding not yet shown)
    ├── AuthFlow.kt (onboarding shown, not logged in)
    │   └── Navigator(LoginScreen)
    └── MainFlow.kt (logged in)
        └── TabNavigator
            ├── HomeTab → ActivityListScreen
            ├── CreateTab → AddActivityScreen
            └── ProfileTab → ProfileScreen
```

- **AppNavigator** (`navigation/AppNavigator.kt`): Root navigator deciding between OnboardingFlow, AuthFlow, and MainFlow
- **OnboardingFlow** (`navigation/OnboardingFlow.kt`): Shown on first launch before auth
- **AuthFlow** (`navigation/AuthFlow.kt`): Handles login/registration screens
- **MainFlow** (`navigation/MainFlow.kt`): Tab-based navigation with Scaffold and bottom NavigationBar
- **Tabs** (`navigation/tabs/`): HomeTab, CreateTab, ProfileTab define tab structure and initial screens

### Design System

Custom design system located in `composeApp/src/commonMain/kotlin/com/togedo/app/designsystem/`:

- **Theme.kt**: `AppTheme` composable provides theme colors and typography via CompositionLocal
  - Access via `AppTheme.colors` and `AppTheme.typography`
  - Platform-specific `SystemAppearance` implemented via expect/actual
- **Color.kt**: Defines `Colors` object with light/dark themes
- **Typography.kt**: Custom typography definitions
- **Components**: Custom components (Button, Card, TextField, Surface, Scaffold, NavigationBar, etc.) in `components/` directory
- **Foundation**: Low-level design tokens (Elevation, Ripple, SystemBarsInsets, etc.) in `foundation/` directory

### Platform-Specific Code

Use expect/actual pattern for platform-specific implementations:
- Place `expect` declarations in `commonMain`
- Place `actual` implementations in platform-specific source sets: `androidMain`, `iosMain`, `jvmMain`, `wasmJsMain`
- Keep shared code platform-agnostic in `commonMain`

Example: `Theme.kt` has `expect fun SystemAppearance(isDark: Boolean)` with actual implementations in each platform's `Theme.<platform>.kt`

### Data Layer

The app uses a domain-driven layering pattern:

- **`domain/model/`** — pure Kotlin data models (e.g., `Activity`)
- **`domain/repository/`** — repository interfaces defining the contract
- **`data/repositoryimpl/`** — concrete repository implementations

DI wires them together in feature modules under `di/`. Each feature has its own module file (`activityModule`, `authModule`, `profileModule`, `appModule`) registered in `di/Koin.kt` via `initKoin()`. ScreenModels are registered as `factoryOf`, repositories as `singleOf`.

### Package Structure

- `com.togedo.app` - root package
  - `data/repositoryimpl/` - repository implementations
  - `designsystem/` - custom design system components and theme
    - `components/` - reusable UI components
    - `foundation/` - design tokens and utilities
  - `di/` - Koin modules per feature + `Koin.kt` entry point
  - `domain/` - models and repository interfaces
  - `navigation/` - navigation flows and tab definitions
  - `ui/` - feature screens organized by domain
    - `auth/` - login and registration screens
    - `activity/list/` - list screen with ScreenModel, State, UiModel
    - `activity/add/` - add activity screen with ScreenModel and State
    - `activity/` - detail, settings, and invite screens
    - `onboarding/` - onboarding flow screens
    - `profile/` - profile and settings screens

## Lumo UI Plugin

The project uses the Lumo UI plugin for design system management. Configuration in `lumo.properties`:
- Theme name: `AppTheme`
- Components directory: `composeApp/src/commonMain/kotlin/com/togedo/app/designsystem`
- Package: `com.togedo.app.designsystem`
- KMP mode: enabled

## Development Guidelines

- Use Koin for dependency injection across all modules
- Use Ktor for networking in shared code
- Default to standard Gradle KMP setup with Kotlin DSL
- Use state management as described in STATE_MANAGEMENT_GUIDE.md (Voyager's StateScreenModel with manual state management)
- Place platform-specific code in expect/actual blocks
- Prefer modern, stable libraries and APIs unless experimental features are explicitly requested
- Do NOT write code comments unless explicitly requested by the user - write self-documenting code with clear naming instead
- **Design tokens**: `ThemeObjects.kt` defines four objects — always use these instead of hardcoded dp values:
  - `Spacing` — padding/margin values (e.g., `Spacing.spacing4` = 16.dp)
  - `Elevation` — shadow/elevation levels (`Elevation.none`, `.low`, `.medium`, `.high`)
  - `BorderRadius` — corner radii (`BorderRadius.roundedSm`, `.roundedMd`, `.roundedLg`, etc.)
  - `ListItemHeight` — standard list item heights (`ListItemHeight.regular` = 64.dp, `.narrow` = 48.dp)

### Visual Design

Before writing any UI code, read these two files:

- **`DESIGN_PRINCIPLES.md`** — defines the visual language: exaggerated minimalism, flat design, tonal elevation (no card borders), bottom navigation placement, touch target sizes, and micro-interaction specs. Apply these rules to every screen and component.
- **`COLOR_SYSTEM.md`** — defines all color tokens with light and dark values. Key rules:
  - Always use `AppTheme.colors.*` — never hardcode hex values in Composables
  - Use `text` / `textSecondary` / `textDisabled` for text colors (not `onBackground` or `onSurface` directly)
  - Cards use tonal elevation — `outline` is for input fields and dividers only
  - `primary` (Berry `#C2456A`) = user; `secondary` (Dusty Purple `#7B5EA7`) = partner — never mix them on the same card

### Kotlin Code Style and Best Practices

Follow official Kotlin coding conventions for consistency and readability:

#### Naming Conventions

- **Classes**: PascalCase, use nouns or noun phrases (e.g., `List`, `PersonReader`, `ActivityViewModel`)
- **Functions**: camelCase starting with lowercase, use verbs or verb phrases (e.g., `close()`, `readPersons()`, `processDeclarations()`)
- **Properties**: camelCase starting with lowercase (e.g., `declarationCount`, `userName`)
- **Constants**: SCREAMING_SNAKE_CASE for `const val` and immutable top-level/object properties (e.g., `MAX_COUNT`, `USER_NAME_FIELD`)
- **Enum constants**: Upper camel case (e.g., `enum class Direction { North, South, East, West }`)
- **Backing properties**: Prefix with underscore (e.g., `private val _elementList`, `val elementList: List<Element> get() = _elementList`)
- **Factory functions**: Use the return type's name (e.g., `fun Foo(): Foo = FooImpl()`)
- **Acronyms**: 2-letter all uppercase (`IOStream`), 3+ only capitalize first letter (`XmlFormatter`, `HttpInputStream`)
- **Test methods**: Can use backticks with spaces `@Test fun \`ensure everything works\`()` or underscores `ensureEverythingWorks_onAndroid()`
- **Avoid vague names**: Don't use `Manager`, `Wrapper`, `Processor` without clear context

#### Formatting

- **Indentation**: Use 4 spaces, never tabs
- **Braces**: Opening brace at end of line, closing brace on new line aligned with construct
- **Semicolons**: Omit semicolons whenever possible
- **Expression bodies**: Use for single expression functions (`fun foo() = 1` instead of `fun foo() { return 1 }`)
- **Unit return type**: Omit `: Unit` when function returns Unit
- **Nullable types**: No space before `?` (e.g., `val text: String?`)
- **Spaces**: Around binary operators (`a + b`), after control flow keywords (`if (condition)`), no space before method/constructor parentheses
- **Trailing commas**: Use for multiline declarations (parameters, arguments, destructuring, etc.)
- **String templates**: Use curly braces for expressions (`"$name has ${children.size} children"`)
- **Multiline strings**: Use `trimIndent()` or `trimMargin()` for proper indentation

#### Code Structure

- **Immutability**: Prefer immutable collections (`List`, `Set`) over mutable types (`ArrayList`, `HashSet`)
- **Expression form**: Use expression form for conditionals when possible (`return if (x) foo() else bar()`)
- **Default parameters**: Use default parameters instead of declaring multiple overloads
- **Named arguments**: Use for multiple parameters of same type or when meaning is unclear (`drawSquare(x = 10, y = 10, width = 100, height = 100, fill = true)`)
- **Range operators**: Use `0..<n` for loops instead of `0..n-1` to avoid off-by-one errors
- **Lambda formatting**: Place parameters on first line with arrow, or on separate lines for long parameter lists
- **Chained calls**: Place `.` or `?.` on next line with single indent
- **Class headers**: Use multiline format for long headers, inheritance, or multiple interfaces

#### Documentation

- **Single-line comments**: `/** This is a short documentation comment. */`
- **Multi-line comments**: Start `/**` on new line, each line begins with `*`
- **Parameter documentation**: Embed inline with `[paramName]` links instead of `@param` tags (e.g., `/** Returns the absolute value of the given [number]. */`)
- **Avoid redundant tags**: Don't use `@param` and `@return` unless descriptions are lengthy

#### Multiplatform Specific

- **File naming**: Use platform suffix for platform-specific top-level declarations (e.g., `Platform.jvm.kt`, `Platform.android.kt`, `Platform.ios.kt`, `Platform.kt` for common)
- **Expect/actual**: Place `expect` in `commonMain`, `actual` in platform-specific source sets

### Kotlin Companion Object Best Practices

- **Accessing companion object members**: Access companion object functions and properties directly through the class name (e.g., `ClassName.function()`, `ClassName.property`)
- **No separate import needed**: Importing a class automatically gives access to its companion object members - no separate companion object import required
- **Callable references**: Create callable references to companion object members using `ClassName::memberName` syntax

