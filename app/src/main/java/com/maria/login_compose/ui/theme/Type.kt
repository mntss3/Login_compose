package com.maria.login_compose.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


// Definir la tipografía
private val Base = Typography() // crea los estilos por defecto de Material 3 y los guarda en Base

val Typography = Base.copy(  // modifica solo los estilos indicados dentro del paréntesis
    headlineMedium = Base.headlineMedium.copy(  // los títulos grandes se quedan con su tamaño original
        fontWeight = FontWeight.Bold, // títulos garndes en negrita
        letterSpacing = (-0.5).sp, // más juntos
    ),
    labelLarge = Base.labelLarge.copy( // estilo de los botones
        fontWeight = FontWeight.SemiBold, // seminegrita
        letterSpacing = 0.5.sp, // separa un poco las letras para una mejor lectura
    ),
)