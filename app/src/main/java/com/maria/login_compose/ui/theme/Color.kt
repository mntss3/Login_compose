package com.maria.login_compose.ui.theme

import androidx.compose.ui.graphics.Color

// Colores de Material Design 3 (esquema claro).
// Son exactamente los mismos "tokens" (variables) que creamos en Figma
// en la colección "M3 Colores": md/primary, md/surface, etc.

val Primary = Color(0xFF0061A4)              // botones y bordes enfocados
val OnPrimary = Color(0xFFFFFFFF)            // texto sobre el botón principal
val PrimaryContainer = Color(0xFFD1E4FF)     // fondo del círculo del logo
val OnPrimaryContainer = Color(0xFF001D36)   // icono del candado
val Surface = Color(0xFFF8FAFF)              // fondo de la pantalla
val OnSurface = Color(0xFF191C20)            // texto principal
val OnSurfaceVariant = Color(0xFF42474E)     // texto secundario
val Outline = Color(0xFF73777F)              // bordes de campos
val OutlineVariant = Color(0xFFC2C7CF)       // líneas divisorias
val Secondary = Color(0xFF535F70)            // fuegos artificiales (gris azulado)
val Tertiary = Color(0xFFE07A5F)             // fuegos artificiales (coral, para que contraste)

// DarkColorScheme

val PrimaryDark = Color(0xFF9ECAFF)              // azul claro: en fondo oscuro se ve mejor

val OnPrimaryDark = Color(0xFF003258)
val PrimaryContainerDark = Color(0xFF00497D)     // círculo del logo
val OnPrimaryContainerDark = Color(0xFFD1E4FF)   // icono del candado
val SurfaceDark = Color(0xFF111418)              // fondo de la pantalla
val OnSurfaceDark = Color(0xFFE1E2E8)            // texto principal
val OnSurfaceVariantDark = Color(0xFFC2C7CF)     // texto secundario
val OutlineDark = Color(0xFF8C9199)              // bordes de campos
val OutlineVariantDark = Color(0xFF42474E)       // líneas divisorias
val SecondaryDark = Color(0xFFBBC7DB)            // gris azulado claro
val TertiaryDark = Color(0xFFFFB4A1)             // coral clarito