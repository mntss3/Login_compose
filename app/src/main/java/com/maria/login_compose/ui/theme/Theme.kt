package com.maria.login_compose.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.ui.graphics.Color
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

// Anima un color: cuando cambia el objetivo, pasa poco a poco del color actual al nuevo.
@Composable
private fun animarColor(objetivo: Color): Color =
    animateColorAsState(
        targetValue = objetivo,
        animationSpec = tween(durationMillis = 500),
        label = "colorTema",
    ).value

@Composable
fun Login_composeTheme(
    // Por defecto sigue el ajuste del móvil (tema claro u oscuro).
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    // Esquema al que queremos llegar
    val objetivo = if (darkTheme) DarkColorScheme else LightColorScheme

    // Copia del esquema con cada color animado: al cambiar de modo,
    // todos se mezclan suavemente durante medio segundo en vez de saltar.
    val colores = objetivo.copy(
        primary = animarColor(objetivo.primary),
        onPrimary = animarColor(objetivo.onPrimary),
        primaryContainer = animarColor(objetivo.primaryContainer),
        onPrimaryContainer = animarColor(objetivo.onPrimaryContainer),
        secondary = animarColor(objetivo.secondary),
        tertiary = animarColor(objetivo.tertiary),
        background = animarColor(objetivo.background),
        onBackground = animarColor(objetivo.onBackground),
        surface = animarColor(objetivo.surface),
        onSurface = animarColor(objetivo.onSurface),
        onSurfaceVariant = animarColor(objetivo.onSurfaceVariant),
        outline = animarColor(objetivo.outline),
        outlineVariant = animarColor(objetivo.outlineVariant),
    )

    MaterialTheme(
        colorScheme = colores,
        typography = Typography,
        content = content,
    )
}