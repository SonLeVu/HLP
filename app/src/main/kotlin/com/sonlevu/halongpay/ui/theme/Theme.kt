package com.sonlevu.halongpay.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryColorYellowDark,
    secondary = SecondaryColorNavyDark,
    tertiary = TertiaryColorNavyDark,
    onPrimary = Color.White,
    secondaryContainer = PrimaryColorYellowDark.copy(alpha = 0.5f),
    surfaceVariant = PrimaryColorYellow.copy(alpha = 0.1f),
    background = darkBackground,
    surface = darkBackground
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryColorYellow,
    secondary = SecondaryColorNavy,
    tertiary = TertiaryColorNavy,
    secondaryContainer = PrimaryColorYellowDark.copy(alpha = 0.5f),
    surfaceVariant = PrimaryColorYellow.copy(alpha = 0.1f),
    background = lightBackground,
    surface = lightBackground

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun HalongpayTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        if (darkTheme) dynamicDarkColorScheme(LocalContext.current)
        else dynamicLightColorScheme(LocalContext.current)
    } else {
        if (darkTheme) DarkColorScheme
        else LightColorScheme
    }

    MaterialTheme(
        colorScheme = colors,
        typography = typography,
        content = content
    )
}