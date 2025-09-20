package com.togedo.app.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.togedo.app.designsystem.components.Icon
import com.togedo.app.designsystem.components.NavigationBar
import com.togedo.app.designsystem.components.NavigationBarItem
import com.togedo.app.designsystem.components.Scaffold
import com.togedo.app.designsystem.components.Text
import com.togedo.app.navigation.tabs.CreateTab
import com.togedo.app.navigation.tabs.HomeTab
import com.togedo.app.navigation.tabs.ProfileTab

@Composable
fun MainFlow() {
    TabNavigator(HomeTab) { tabNavigator ->
        Scaffold(
            bottomBar = {
                NavigationBar {
                    TabNavigationItem(HomeTab)
                    TabNavigationItem(CreateTab)
                    TabNavigationItem(ProfileTab)
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                CurrentTab()
            }
        }
    }
}

@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current

    NavigationBarItem(
        selected = tabNavigator.current == tab,
        onClick = { tabNavigator.current = tab },
        icon = { Icon(painter = tab.options.icon!!, contentDescription = tab.options.title) },
        label = { Text(text = tab.options.title) })
}
