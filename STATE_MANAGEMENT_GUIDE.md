# State Management with Voyager's StateScreenModel

This guide shows how state management is implemented in this project using the Activity List screen as an example.

## Architecture Overview

```
┌──────────────────────┐
│  ActivityListScreen  │  ← Composable UI (observes state)
│    (View Layer)      │
└──────────┬───────────┘
           │ collectAsState()
           ↓
┌──────────────────────┐
│ ActivityListScreen   │  ← Manages state (StateFlow)
│       Model          │     Handles user actions
└──────────┬───────────┘
           │
           ↓
┌──────────────────────┐
│  ActivityListState   │  ← Data class with all UI state
└──────────────────────┘
```

## Files Structure

```
ui/activity/list/
├── ActivityListScreen.kt         ← UI composables
├── ActivityListScreenModel.kt    ← State management
├── ActivityListState.kt          ← State data class
└── ActivityUiModel.kt            ← UI model for activities
```

## How It Works

### 1. State Definition (`ActivityListState.kt`)

```kotlin
data class ActivityListState(
    val activities: List<ActivityUiModel> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedFilter: ActivityFilter? = null
)
```

**Key Points:**
- Immutable data class
- Contains ALL UI state in one place
- Default values for initialization

### 2. ScreenModel (`ActivityListScreenModel.kt`)

```kotlin
class ActivityListScreenModel : StateScreenModel<ActivityListState>(ActivityListState()) {

    init {
        loadActivities()  // Load data on creation
    }

    fun loadActivities() {
        screenModelScope.launch {
            // 1. Set loading state
            mutableState.value = state.value.copy(isLoading = true)

            try {
                // 2. Fetch data
                val activities = getSampleActivities()

                // 3. Update state with success
                mutableState.value = state.value.copy(
                    activities = activities,
                    isLoading = false
                )
            } catch (e: Exception) {
                // 4. Update state with error
                mutableState.value = state.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        mutableState.value = state.value.copy(searchQuery = query)
    }
}
```

**Key Points:**
- Extends `StateScreenModel<State>` from Voyager
- `mutableState.value` = modify state
- `state.value` = read current state
- `screenModelScope` = coroutine scope tied to screen lifecycle
- Always use `.copy()` to update state immutably

### 3. UI Layer (`ActivityListScreen.kt`)

```kotlin
class ActivityListScreen : Screen {
    @Composable
    override fun Content() {
        // 1. Get the ScreenModel
        val screenModel = getScreenModel<ActivityListScreenModel>()

        // 2. Collect state as Compose State
        val state by screenModel.state.collectAsState()

        // 3. Pass state and callbacks to UI
        ActivityListContent(
            state = state,
            onSearchQueryChanged = screenModel::onSearchQueryChanged,
            onRefresh = screenModel::loadActivities
        )
    }

    @Composable
    fun ActivityListContent(
        state: ActivityListState,
        onSearchQueryChanged: (String) -> Unit,
        onRefresh: () -> Unit
    ) {
        // 4. Render based on state
        when {
            state.isLoading -> LoadingIndicator()
            state.error != null -> ErrorMessage(state.error, onRefresh)
            else -> ActivityList(state.activities)
        }
    }
}
```

**Key Points:**
- `getScreenModel<T>()` = creates or retrieves ScreenModel
- `collectAsState()` = converts StateFlow to Compose State
- UI recomposes automatically when state changes
- Pass callbacks (not ScreenModel) to child composables

## Benefits of This Approach

### ✅ Pros
- **Simple**: No external libraries needed (uses Voyager's built-in StateScreenModel)
- **Type-safe**: Kotlin's type system catches errors at compile time
- **Testable**: Easy to test ScreenModel in isolation
- **Single source of truth**: All state in one place
- **Lifecycle-aware**: ScreenModel survives configuration changes
- **Immutable state**: Using `.copy()` prevents accidental mutations

### ❌ Cons
- Can get verbose with complex state
- Need to remember to use `.copy()` every time
- Side effects (navigation, toasts) mixed with state updates

## Testing

### Testing ScreenModel

```kotlin
class ActivityListScreenModelTest {

    @Test
    fun `initial state is loading`() {
        val screenModel = ActivityListScreenModel()

        val state = screenModel.state.value
        assertTrue(state.isLoading)
        assertTrue(state.activities.isEmpty())
    }

    @Test
    fun `search query updates state`() = runTest {
        val screenModel = ActivityListScreenModel()

        screenModel.onSearchQueryChanged("hiking")

        assertEquals("hiking", screenModel.state.value.searchQuery)
    }

    @Test
    fun `loadActivities populates state`() = runTest {
        val screenModel = ActivityListScreenModel()

        // Wait for init block to complete
        advanceUntilIdle()

        assertFalse(screenModel.state.value.isLoading)
        assertTrue(screenModel.state.value.activities.isNotEmpty())
    }
}
```

## Resources

- [Voyager Documentation](https://voyager.adriel.cafe/)
- [Kotlin Flows](https://kotlinlang.org/docs/flow.html)
- [Compose State Management](https://developer.android.com/jetpack/compose/state)
