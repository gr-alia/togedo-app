package com.togedo.app.navigation.tabs

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.togedo.app.ui.profile.ProfileScreen
import compose.icons.FeatherIcons
import compose.icons.feathericons.User

object ProfileTab : Tab {
    override val options: TabOptions
        @Composable get() {
            val painter = rememberVectorPainter(image = FeatherIcons.User)
            return TabOptions(
                index = 2u,
                title = "Profile",
                icon = painter
            )
        }

    @Composable
    override fun Content() {
        Navigator(ProfileScreen())
    }
}
