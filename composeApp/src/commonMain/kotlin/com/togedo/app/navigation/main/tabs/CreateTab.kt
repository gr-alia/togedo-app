package com.togedo.app.navigation.main.tabs

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.togedo.app.navigation.main.screens.CreateActivityScreen
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.vectorResource
import togedo_app.composeapp.generated.resources.Res
import togedo_app.composeapp.generated.resources.ic_rotate_right

object CreateTab : Tab {
    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 1u,
            title = "Create",
            icon = painterResource(Res.drawable.ic_rotate_right)
        )

    @Composable
    override fun Content() {
        Navigator(CreateActivityScreen())
    }
}
