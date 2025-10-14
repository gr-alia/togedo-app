package com.togedo.app.navigation.tabs

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.togedo.app.ui.activity.add.AddActivityScreen
import compose.icons.FeatherIcons
import compose.icons.feathericons.Plus

object CreateTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val painter = rememberVectorPainter(image = FeatherIcons.Plus)
            return TabOptions(
                index = 1u,
                title = "Create",
                icon = painter
            )
        }

    @Composable
    override fun Content() {
        Navigator(AddActivityScreen())
    }
}
