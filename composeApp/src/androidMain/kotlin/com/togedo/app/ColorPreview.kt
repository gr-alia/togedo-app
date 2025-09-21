package com.togedo.app

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

internal val Black: Color = Color(0xFF000000)
internal val Gray900: Color = Color(0xFF282828)
internal val Gray800: Color = Color(0xFF4b4b4b)
internal val Gray700: Color = Color(0xFF5e5e5e)
internal val Gray600: Color = Color(0xFF727272)
internal val Gray500: Color = Color(0xFF868686)
internal val Gray400: Color = Color(0xFFC7C7C7)
internal val Gray300: Color = Color(0xFFDFDFDF)
internal val Gray200: Color = Color(0xFFE2E2E2)
internal val Gray100: Color = Color(0xFFF7F7F7)
internal val Gray50: Color = Color(0xFFFFFFFF)
internal val White: Color = Color(0xFFFFFFFF)

internal val Red900: Color = Color(0xFF520810)
internal val Red800: Color = Color(0xFF950f22)
internal val Red700: Color = Color(0xFFbb032a)
internal val Red600: Color = Color(0xFFde1135)
internal val Red500: Color = Color(0xFFf83446)
internal val Red400: Color = Color(0xFFfc7f79)
internal val Red300: Color = Color(0xFFffb2ab)
internal val Red200: Color = Color(0xFFffd2cd)
internal val Red100: Color = Color(0xFFffe1de)
internal val Red50: Color = Color(0xFFfff0ee)

internal val Blue900: Color = Color(0xFF276EF1)
internal val Blue800: Color = Color(0xFF3F7EF2)
internal val Blue700: Color = Color(0xFF578EF4)
internal val Blue600: Color = Color(0xFF6F9EF5)
internal val Blue500: Color = Color(0xFF87AEF7)
internal val Blue400: Color = Color(0xFF9FBFF8)
internal val Blue300: Color = Color(0xFFB7CEFA)
internal val Blue200: Color = Color(0xFFCFDEFB)
internal val Blue100: Color = Color(0xFFE7EEFD)
internal val Blue50: Color = Color(0xFFFFFFFF)

internal val Green950: Color = Color(0xFF0B4627)
internal val Green900: Color = Color(0xFF16643B)
internal val Green800: Color = Color(0xFF1A7544)
internal val Green700: Color = Color(0xFF178C4E)
internal val Green600: Color = Color(0xFF1DAF61)
internal val Green500: Color = Color(0xFF1FC16B)
internal val Green400: Color = Color(0xFF3EE089)
internal val Green300: Color = Color(0xFF84EBB4)
internal val Green200: Color = Color(0xFFC2F5DA)
internal val Green100: Color = Color(0xFFD0FBE9)
internal val Green50: Color = Color(0xFFE0FAEC)

internal val PurpleIntense: Color = Color(0xFF3E1E68)
internal val Purple: Color = Color(0xFF5D2F77)
internal val PinkIntense: Color = Color(0xFFE45A92)
internal val Pink: Color = Color(0xFFFFACAC)

@Immutable
internal data class Colors(
    val primary: Color,
    val onPrimary: Color,
    val secondary: Color,
    val onSecondary: Color,
    val tertiary: Color,
    val onTertiary: Color,
    val error: Color,
    val onError: Color,
    val success: Color,
    val onSuccess: Color,
    val disabled: Color,
    val onDisabled: Color,
    val surface: Color,
    val onSurface: Color,
    val background: Color,
    val onBackground: Color,
    val outline: Color,
    val transparent: Color = Color.Transparent,
    val white: Color = White,
    val black: Color = Black,
    val text: Color,
    val textSecondary: Color,
    val textDisabled: Color,
    val scrim: Color,
    val elevation: Color,
)

