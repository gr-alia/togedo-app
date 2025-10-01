package com.togedo.app.navigation.tabs

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.togedo.app.ui.activity.list.ActivityListScreen
import compose.icons.FeatherIcons
import compose.icons.feathericons.Home

object HomeTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val painter = rememberVectorPainter(image = FeatherIcons.Home)
            return TabOptions(
                index = 0u,
                title = "Home",
                icon = painter
            )
        }

    @Composable
    override fun Content() {
        Navigator(ActivityListScreen())
    }
}
