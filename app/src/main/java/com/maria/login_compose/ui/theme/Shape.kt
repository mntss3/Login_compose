package com.maria.login_compose.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val Formas = Shapes( // cuánto se redondean las esquinas de los elementos del login
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(14.dp),   // campos de texto
    large = RoundedCornerShape(28.dp),    // tarjeta de cristal
)