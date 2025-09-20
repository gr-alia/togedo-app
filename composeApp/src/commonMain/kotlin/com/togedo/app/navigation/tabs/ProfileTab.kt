package com.togedo.app.navigation.tabs

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.togedo.app.ui.profile.ProfileScreen
import org.jetbrains.compose.resources.painterResource
import togedo_app.composeapp.generated.resources.Res
import togedo_app.composeapp.generated.resources.ic_dark_mode

object ProfileTab : Tab {
    override val options: TabOptions
        @Composable get() = TabOptions(
            index = 2u, title = "Profile", icon = painterResource(Res.drawable.ic_dark_mode)
        )

    @Composable
    override fun Content() {
        Navigator(ProfileScreen())
    }
}
