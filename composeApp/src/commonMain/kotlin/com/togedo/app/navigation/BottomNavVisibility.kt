package com.togedo.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.Navigator

val LocalBottomNavHiddenCount = compositionLocalOf { mutableIntStateOf(0) }

@Composable
fun NavigatorDepthObserver(navigator: Navigator) {
    val count = LocalBottomNavHiddenCount.current
    val isDeep = remember(navigator) { derivedStateOf { navigator.size > 1 } }.value
    DisposableEffect(isDeep) {
        if (isDeep) count.value++
        onDispose { if (isDeep) count.value-- }
    }
}
