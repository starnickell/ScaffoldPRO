package com.scaffoldpro.app.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColors = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    primaryContainer = md_theme_light_primaryContainer,
    onPrimaryContainer = md_theme_light_onPrimaryContainer,
    secondary = md_theme_light_secondary,
    onSecondary = md_theme_light_onSecondary,
    secondaryContainer = md_theme_light_secondaryContainer,
    onSecondaryContainer = md_theme_light_onSecondaryContainer,
    tertiary = md_theme_light_tertiary,
    onTertiary = md_theme_light_onTertiary,
    tertiaryContainer = md_theme_light_tertiaryContainer,
    onTertiaryContainer = md_theme_light_onTertiaryContainer,
    error = md_theme_light_error,
    errorContainer = md_theme_light_errorContainer,
    onError = md_theme_light_onError,
    onErrorContainer = md_theme_light_onErrorContainer,
    background = md_theme_light_background,
    onBackground = md_theme_light_onBackground,
    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface,
    surfaceVariant = md_theme_light_surfaceVariant,
    onSurfaceVariant = md_theme_light_onSurfaceVariant,
    outline = md_theme_light_outline,
    inverseOnSurface = md_theme_light_inverseOnSurface,
    inverseSurface = md_theme_light_inverseSurface,
    inversePrimary = md_theme_light_inversePrimary,
    surfaceTint = md_theme_light_surfaceTint,
    outlineVariant = md_theme_light_outlineVariant,
    scrim = md_theme_light_scrim,
)


private val DarkColors = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    primaryContainer = md_theme_dark_primaryContainer,
    onPrimaryContainer = md_theme_dark_onPrimaryContainer,
    secondary = md_theme_dark_secondary,
    onSecondary = md_theme_dark_onSecondary,
    secondaryContainer = md_theme_dark_secondaryContainer,
    onSecondaryContainer = md_theme_dark_onSecondaryContainer,
    tertiary = md_theme_dark_tertiary,
    onTertiary = md_theme_dark_onTertiary,
    tertiaryContainer = md_theme_dark_tertiaryContainer,
    onTertiaryContainer = md_theme_dark_onTertiaryContainer,
    error = md_theme_dark_error,
    errorContainer = md_theme_dark_errorContainer,
    onError = md_theme_dark_onError,
    onErrorContainer = md_theme_dark_onErrorContainer,
    background = md_theme_dark_background,
    onBackground = md_theme_dark_onBackground,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface,
    surfaceVariant = md_theme_dark_surfaceVariant,
    onSurfaceVariant = md_theme_dark_onSurfaceVariant,
    outline = md_theme_dark_outline,
    inverseOnSurface = md_theme_dark_inverseOnSurface,
    inverseSurface = md_theme_dark_inverseSurface,
    inversePrimary = md_theme_dark_inversePrimary,
    surfaceTint = md_theme_dark_surfaceTint,
    outlineVariant = md_theme_dark_outlineVariant,
    scrim = md_theme_dark_scrim,
)

@Composable
fun ScaffoldPROTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android S+ (API 31+)
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColors
        else -> LightColors
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb() // Or any other color
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    // Verify WCAG AA compliance for critical color combinations
    validateWCAGCompliance(colorScheme)

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // Assuming Typography.kt is defined elsewhere
        content = content
    )
}

/**
 * Validates that the color scheme meets WCAG AA compliance standards.
 * This function only runs in debug builds to help catch accessibility issues early.
 *
 * @param colorScheme The MaterialTheme color scheme to validate
 */
private fun validateWCAGCompliance(colorScheme: androidx.compose.material3.ColorScheme) {
    if (BuildConfig.DEBUG) {
        // Check text contrast ratios
        val textOnBackgroundRatio = calculateContrastRatio(
            colorScheme.onBackground.toArgb(), 
            colorScheme.background.toArgb()
        )
        val textOnSurfaceRatio = calculateContrastRatio(
            colorScheme.onSurface.toArgb(), 
            colorScheme.surface.toArgb()
        )
        val textOnPrimaryRatio = calculateContrastRatio(
            colorScheme.onPrimary.toArgb(), 
            colorScheme.primary.toArgb()
        )
        
        // Log warnings for any contrast issues
        if (textOnBackgroundRatio < 4.5) {
            Log.w("ScaffoldPROTheme", "WCAG AA Compliance: Text on background contrast ratio is only $textOnBackgroundRatio:1 (should be at least 4.5:1)")
        }
        if (textOnSurfaceRatio < 4.5) {
            Log.w("ScaffoldPROTheme", "WCAG AA Compliance: Text on surface contrast ratio is only $textOnSurfaceRatio:1 (should be at least 4.5:1)")
        }
        if (textOnPrimaryRatio < 4.5) {
            Log.w("ScaffoldPROTheme", "WCAG AA Compliance: Text on primary contrast ratio is only $textOnPrimaryRatio:1 (should be at least 4.5:1)")
        }
    }
}

/**
 * Calculates the contrast ratio between two colors according to WCAG guidelines.
 * 
 * @param foreground The foreground color (text) as an ARGB integer
 * @param background The background color as an ARGB integer
 * @return The contrast ratio (1-21)
 */
private fun calculateContrastRatio(foreground: Int, background: Int): Double {
    val foregroundLuminance = calculateRelativeLuminance(foreground)
    val backgroundLuminance = calculateRelativeLuminance(background)
    
    val lighter = maxOf(foregroundLuminance, backgroundLuminance)
    val darker = minOf(foregroundLuminance, backgroundLuminance)
    
    return (lighter + 0.05) / (darker + 0.05)
}

/**
 * Calculates the relative luminance of a color.
 * 
 * @param colorInt The color as an ARGB integer
 * @return The relative luminance value (0-1)
 */
private fun calculateRelativeLuminance(colorInt: Int): Double {
    // Extract RGB components
    val red = ((colorInt and 0xFF0000) shr 16) / 255.0
    val green = ((colorInt and 0x00FF00) shr 8) / 255.0
    val blue = (colorInt and 0x0000FF) / 255.0
    
    // Apply gamma correction
    val r = if (red <= 0.03928) red/12.92 else Math.pow((red + 0.055)/1.055, 2.4)
    val g = if (green <= 0.03928) green/12.92 else Math.pow((green + 0.055)/1.055, 2.4)
    val b = if (blue <= 0.03928) blue/12.92 else Math.pow((blue + 0.055)/1.055, 2.4)
    
    // Calculate luminance using the formula
    return 0.2126 * r + 0.7152 * g + 0.0722 * b
}

