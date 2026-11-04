package com.maria.login_compose.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

// Un "ColorScheme" asigna cada color a su ROL dentro de Material 3.
// Los componentes (Button, OutlinedTextField...) leen estos roles solos,
// por eso no hace falta pintar cada uno a mano.

// ---- Modo claro ----
private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    secondary = Secondary,
    tertiary = Tertiary,
    primaryContainer = PrimaryContainer,
    onPrimaryContainer = OnPrimaryContainer,
    surface = Surface,
    onSurface = OnSurface,
    onSurfaceVariant = OnSurfaceVariant,
    outline = Outline,
    outlineVariant = OutlineVariant,
    // El fondo del Scaffold usa "background": lo igualamos a "surface"
    // para que la pantalla tenga el mismo color que en Figma.
    background = Surface,
    onBackground = OnSurface,
)


// ---- Modo oscuro ----
// Mismos roles que el claro, pero con los colores pensados para fondo oscuro.
private val DarkColorScheme = darkColorScheme(
    primary = PrimaryDark,
    onPrimary = OnPrimaryDark,
    secondary = SecondaryDark,
    tertiary = TertiaryDark,
    primaryContainer = PrimaryContainerDark,
    onPrimaryContainer = OnPrimaryContainerDark,
    surface = SurfaceDark,
    onSurface = OnSurfaceDark,
    onSurfaceVariant = OnSurfaceVariantDark,
    outline = OutlineDark,
    outlineVariant = OutlineVariantDark,
    background = SurfaceDark,
    onBackground = OnSurfaceDark,
)

@Composable
fun Login_composeTheme(
    // Por defecto sigue el ajuste del móvil (tema claro u oscuro).
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    // Sin color dinámico: la app se ve igual en todos los móviles.
    val colores = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colores,
        typography = Typography,
        content = content,
    )
}