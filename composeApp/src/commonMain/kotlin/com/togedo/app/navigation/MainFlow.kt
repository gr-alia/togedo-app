package com.togedo.app.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.togedo.app.designsystem.AppTheme
import com.togedo.app.designsystem.components.Icon
import com.togedo.app.designsystem.components.NavigationBarDefaults.NavigationBarHeight
import com.togedo.app.designsystem.components.NavigationBarDefaults.windowInsets
import com.togedo.app.designsystem.components.NavigationBarItem
import com.togedo.app.designsystem.components.NavigationBarItemDefaults
import com.togedo.app.designsystem.components.Scaffold
import com.togedo.app.designsystem.components.Surface
import com.togedo.app.designsystem.components.Text
import com.togedo.app.navigation.tabs.CreateTab
import com.togedo.app.navigation.tabs.HomeTab
import com.togedo.app.navigation.tabs.ProfileTab
import compose.icons.FeatherIcons
import compose.icons.feathericons.Plus

private val FabSize = 56.dp

@Composable
fun MainFlow() {
    val hiddenCount = remember { mutableIntStateOf(0) }

    CompositionLocalProvider(LocalBottomNavHiddenCount provides hiddenCount) {
        TabNavigator(HomeTab) { tabNavigator ->
            val showBottomNav = hiddenCount.value == 0 && tabNavigator.current != CreateTab

            Box(modifier = Modifier.fillMaxSize()) {
                Scaffold { paddingValues ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                bottom = paddingValues.calculateBottomPadding(),
                                top = paddingValues.calculateTopPadding(),
                            )
                    ) {
                        CurrentTab()
                    }
                }

                if (showBottomNav) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                    ) {
                        MainNavigationBar(onCreateClick = { tabNavigator.current = CreateTab })
                    }
                }
            }
        }
    }
}

@Composable
private fun MainNavigationBar(onCreateClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Surface(
            color = AppTheme.colors.background,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(windowInsets)
                    .height(NavigationBarHeight)
                    .selectableGroup(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TabNavItem(HomeTab, Modifier.weight(1f))
                Spacer(Modifier.weight(1f))
                TabNavItem(ProfileTab, Modifier.weight(1f))
            }
        }

        Box(
            modifier = Modifier
                .size(FabSize)
                .align(Alignment.TopCenter)
                .offset(y = -(FabSize / 2))
                .shadow(elevation = 8.dp, shape = CircleShape, ambientColor = AppTheme.colors.primary.copy(alpha = 0.4f), spotColor = AppTheme.colors.primary.copy(alpha = 0.4f))
                .clip(CircleShape)
                .background(AppTheme.colors.primary)
                .clickable(onClick = onCreateClick),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = FeatherIcons.Plus,
                contentDescription = "Create",
                tint = Color.White,
                modifier = Modifier.size(24.dp),
            )
        }
    }
}

@Composable
private fun RowScope.TabNavItem(tab: Tab, modifier: Modifier = Modifier) {
    val tabNavigator = LocalTabNavigator.current
    val selectedColor = AppTheme.colors.primary

    NavigationBarItem(
        modifier = modifier,
        selected = tabNavigator.current == tab,
        onClick = { tabNavigator.current = tab },
        icon = { Icon(painter = tab.options.icon!!, contentDescription = tab.options.title) },
        label = { Text(text = tab.options.title) },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = selectedColor,
            selectedTextColor = selectedColor,
        ),
    )
}
