package com.maria.login_compose.ui

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.unit.dp

// ¿Modo oscuro? Lo deducimos de lo oscuro que es el fondo del tema.
@Composable
private fun esModoOscuro(): Boolean = MaterialTheme.colorScheme.surface.luminance() < 0.5f

// Manchas de luz de colores que flotan despacio al fondo. Solo decoración.
@Composable
fun FondoCristal(modifier: Modifier = Modifier) {
    val violeta = MaterialTheme.colorScheme.primary
    val cian = MaterialTheme.colorScheme.secondary
    val magenta = MaterialTheme.colorScheme.tertiary
    val intensidad = if (esModoOscuro()) 0.45f else 0.30f

    val transicion = rememberInfiniteTransition(label = "manchas")
    val t by transicion.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 9000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "movimientoManchas",
    )

    Canvas(modifier = modifier.fillMaxSize()) {
        val w = size.width
        val h = size.height
        drawMancha(Offset(w * (0.15f + 0.10f * t), h * 0.18f), w * 0.60f, violeta.copy(alpha = intensidad))
        drawMancha(Offset(w * (0.95f - 0.10f * t), h * (0.40f + 0.05f * t)), w * 0.55f, magenta.copy(alpha = intensidad))
        drawMancha(Offset(w * (0.30f + 0.15f * t), h * 0.85f), w * 0.65f, cian.copy(alpha = intensidad))
    }
}

// Un círculo cuyo color se desvanece hacia los bordes.
private fun DrawScope.drawMancha(centro: Offset, radio: Float, color: Color) {
    drawCircle(
        brush = Brush.radialGradient(
            colors = listOf(color, Color.Transparent),
            center = centro,
            radius = radio,
        ),
        radius = radio,
        center = centro,
    )
}

// Panel semitransparente con borde brillante. Funciona como una Column.
@Composable
fun TarjetaCristal(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    val oscuro = esModoOscuro()
    val forma = MaterialTheme.shapes.large
    val relleno = Color.White.copy(alpha = if (oscuro) 0.07f else 0.55f)
    val borde = Brush.linearGradient(
        listOf(
            Color.White.copy(alpha = if (oscuro) 0.35f else 0.9f),
            Color.White.copy(alpha = if (oscuro) 0.05f else 0.3f),
        )
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(forma)
            .background(relleno)
            .border(width = 1.dp, brush = borde, shape = forma)
            .padding(20.dp),
        content = content,
    )
}