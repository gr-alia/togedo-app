package com.togedo.app.navigation.tabs

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.togedo.app.ui.activity.ActivityListScreen
import org.jetbrains.compose.resources.painterResource
import togedo_app.composeapp.generated.resources.Res
import togedo_app.composeapp.generated.resources.ic_cyclone

object HomeTab : Tab {
    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 0u,
            title = "Home",
            icon = painterResource(Res.drawable.ic_cyclone)
        )

    @Composable
    override fun Content() {
        Navigator(ActivityListScreen())
    }
}
