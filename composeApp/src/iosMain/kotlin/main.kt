import androidx.compose.ui.window.ComposeUIViewController
import com.togedo.app.App
import com.togedo.app.di.initKoin
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController (configure = { initKoin() }) { App() }