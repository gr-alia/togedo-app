package com.togedo.app.designsystem

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// Berry (user's color)
val DeepBerry: Color = Color(0xFFC2456A)
val SoftBerry: Color = Color(0xFFF4A0BC)
val BlushTint: Color = Color(0xFFFAEAEE)
val DeepBerryDark: Color = Color(0xFF3A1828)

// Dusty Purple (partner's color)
val DustyPurple: Color = Color(0xFF7B5EA7)
val SoftPurple: Color = Color(0xFFC5AEE8)
val LavenderTint: Color = Color(0xFFF0EBF8)
val DeepPurpleDark: Color = Color(0xFF251838)

// Backgrounds
val IvoryBlush: Color = Color(0xFFFAF7F2)
val DeepPlum: Color = Color(0xFF1A1220)
val DarkSurface: Color = Color(0xFF241828)

// Text hierarchy
val NearBlack: Color = Color(0xFF1E1218)
val DeepRose: Color = Color(0xFF4A2C38)
val MutedRose: Color = Color(0xFF7A5060)
val SoftBlush: Color = Color(0xFFF0E6F0)
val WarmLilac: Color = Color(0xFFDBC8D8)
val MutedLilac: Color = Color(0xFFA890A8)

// Tertiary / accent
val EarthYellow: Color = Color(0xFFEFB46E)
val WarmYellow: Color = Color(0xFFF5C878)
val DarkBrown: Color = Color(0xFF3D2200)

// Borders
val BorderDefault: Color = Color(0xFFE8E0D8)
val BorderLight: Color = Color(0xFFF0EAE4)
val BorderDark: Color = Color(0xFF3A2840)
val BorderDarkVariant: Color = Color(0xFF2C2035)

// Error (warm raspberry)
val WarmRaspberry: Color = Color(0xFFB5274A)
val SoftRaspberry: Color = Color(0xFFF2849C)
val ErrorContainerLight: Color = Color(0xFFFCEEF2)
val ErrorContainerDark: Color = Color(0xFF4A1028)
val OnErrorContainerLight: Color = Color(0xFF7A1030)

// Success (sage green)
val SageGreen: Color = Color(0xFF3D7A52)
val SoftSage: Color = Color(0xFF7EC89A)
val SuccessContainerLight: Color = Color(0xFFEAF4EE)
val SuccessContainerDark: Color = Color(0xFF0E3020)
val OnSuccessContainerLight: Color = Color(0xFF1E4A30)

// Disabled (warm rose-grey)
val RoseGrey: Color = Color(0xFFC8B8C0)
val RoseGreyDark: Color = Color(0xFF4A3A42)
val DisabledContainerLight: Color = Color(0xFFEDE6E8)
val DisabledContainerDark: Color = Color(0xFF302028)
val OnDisabledContainerLight: Color = Color(0xFFA898A0)
val OnDisabledContainerDark: Color = Color(0xFF6A5860)
val OnDisabledDark: Color = Color(0xFF7A6870)

// Legacy brand colors still referenced in LoginScreen.kt and ProfileScreen.kt
val Verdigris: Color = Color(0xFF45B3AC)
val UtOrange: Color = Color(0xFFF98B29)

val White: Color = Color(0xFFFFFFFF)
val Black: Color = Color(0xFF000000)

@Immutable
data class Colors(
    val primary: Color,
    val onPrimary: Color,
    val secondary: Color,
    val onSecondary: Color,
    val tertiary: Color,
    val onTertiary: Color,
    val error: Color,
    val onError: Color,
    val errorContainer: Color,
    val onErrorContainer: Color,
    val success: Color,
    val onSuccess: Color,
    val successContainer: Color,
    val onSuccessContainer: Color,
    val disabled: Color,
    val onDisabled: Color,
    val disabledContainer: Color,
    val onDisabledContainer: Color,
    val surface: Color,
    val onSurface: Color,
    val onSurfaceVariant: Color,
    val background: Color,
    val onBackground: Color,
    val primaryContainer: Color,
    val onPrimaryContainer: Color,
    val secondaryContainer: Color,
    val onSecondaryContainer: Color,
    val outline: Color,
    val outlineVariant: Color,
    val transparent: Color = Color.Transparent,
    val white: Color = White,
    val black: Color = Black,
    val text: Color,
    val textSecondary: Color,
    val textDisabled: Color,
    val scrim: Color,
    val elevation: Color,
    val featureSpecificStatusPlanning: Color,
    val onFeatureSpecificStatusPlanning: Color,
    val featureSpecificStatusPlanned: Color,
    val onFeatureSpecificStatusPlanned: Color,
    val featureSpecificStatusCanceled: Color,
    val onFeatureSpecificStatusCanceled: Color,
    val featureSpecificStatusDone: Color,
    val onFeatureSpecificStatusDone: Color,
)

internal val LightColors = Colors(
    primary = DeepBerry,
    onPrimary = White,
    secondary = DustyPurple,
    onSecondary = White,
    tertiary = EarthYellow,
    onTertiary = DarkBrown,
    surface = White,
    onSurface = NearBlack,
    onSurfaceVariant = MutedRose,
    background = IvoryBlush,
    onBackground = NearBlack,
    primaryContainer = BlushTint,
    onPrimaryContainer = DeepBerry,
    secondaryContainer = LavenderTint,
    onSecondaryContainer = DustyPurple,
    outline = BorderDefault,
    outlineVariant = BorderLight,
    error = WarmRaspberry,
    onError = White,
    errorContainer = ErrorContainerLight,
    onErrorContainer = OnErrorContainerLight,
    success = SageGreen,
    onSuccess = White,
    successContainer = SuccessContainerLight,
    onSuccessContainer = OnSuccessContainerLight,
    disabled = RoseGrey,
    onDisabled = White,
    disabledContainer = DisabledContainerLight,
    onDisabledContainer = OnDisabledContainerLight,
    text = NearBlack,
    textSecondary = DeepRose,
    textDisabled = MutedRose,
    scrim = Color.Black.copy(alpha = 0.32f),
    elevation = MutedRose,
    featureSpecificStatusPlanning = DisabledContainerLight,
    onFeatureSpecificStatusPlanning = NearBlack,
    featureSpecificStatusPlanned = LavenderTint,
    onFeatureSpecificStatusPlanned = DustyPurple,
    featureSpecificStatusCanceled = ErrorContainerLight,
    onFeatureSpecificStatusCanceled = WarmRaspberry,
    featureSpecificStatusDone = SuccessContainerLight,
    onFeatureSpecificStatusDone = SageGreen,
)

internal val DarkColors = Colors(
    primary = SoftBerry,
    onPrimary = DeepPlum,
    secondary = SoftPurple,
    onSecondary = DeepPlum,
    tertiary = WarmYellow,
    onTertiary = DeepPlum,
    surface = DarkSurface,
    onSurface = WarmLilac,
    onSurfaceVariant = MutedLilac,
    background = DeepPlum,
    onBackground = SoftBlush,
    primaryContainer = DeepBerryDark,
    onPrimaryContainer = SoftBerry,
    secondaryContainer = DeepPurpleDark,
    onSecondaryContainer = SoftPurple,
    outline = BorderDark,
    outlineVariant = BorderDarkVariant,
    error = SoftRaspberry,
    onError = DeepPlum,
    errorContainer = ErrorContainerDark,
    onErrorContainer = SoftRaspberry,
    success = SoftSage,
    onSuccess = DeepPlum,
    successContainer = SuccessContainerDark,
    onSuccessContainer = SoftSage,
    disabled = RoseGreyDark,
    onDisabled = OnDisabledDark,
    disabledContainer = DisabledContainerDark,
    onDisabledContainer = OnDisabledContainerDark,
    text = SoftBlush,
    textSecondary = WarmLilac,
    textDisabled = MutedLilac,
    scrim = Color.Black.copy(alpha = 0.72f),
    elevation = MutedLilac,
    featureSpecificStatusPlanning = DisabledContainerDark,
    onFeatureSpecificStatusPlanning = SoftBlush,
    featureSpecificStatusPlanned = DeepPurpleDark,
    onFeatureSpecificStatusPlanned = SoftPurple,
    featureSpecificStatusCanceled = ErrorContainerDark,
    onFeatureSpecificStatusCanceled = SoftRaspberry,
    featureSpecificStatusDone = SuccessContainerDark,
    onFeatureSpecificStatusDone = SoftSage,
)

val LocalColors = staticCompositionLocalOf { LightColors }
val LocalContentColor = compositionLocalOf { Color.Black }
val LocalContentAlpha = compositionLocalOf { 1f }

fun Colors.contentColorFor(backgroundColor: Color): Color {
    return when (backgroundColor) {
        primary -> onPrimary
        secondary -> onSecondary
        tertiary -> onTertiary
        surface -> onSurface
        error -> onError
        success -> onSuccess
        disabled -> onDisabled
        background -> onBackground
        primaryContainer -> onPrimaryContainer
        secondaryContainer -> onSecondaryContainer
        errorContainer -> onErrorContainer
        successContainer -> onSuccessContainer
        disabledContainer -> onDisabledContainer
        else -> Color.Unspecified
    }
}
