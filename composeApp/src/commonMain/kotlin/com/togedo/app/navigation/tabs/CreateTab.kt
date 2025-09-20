package com.togedo.app.navigation.tabs

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.togedo.app.ui.activity.CreateActivityScreen
import org.jetbrains.compose.resources.painterResource
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
