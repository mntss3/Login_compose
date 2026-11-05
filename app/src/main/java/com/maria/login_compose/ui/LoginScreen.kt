package com.maria.login_compose.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.maria.login_compose.R
import com.maria.login_compose.ui.theme.Login_composeTheme
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember

/*
 * PANTALLA 1: LOGIN
 *
 * Idea clave de Compose: la pantalla es una FUNCIÓN que dibuja la interfaz
 * a partir de un ESTADO. Cuando el estado cambia (por ejemplo, al escribir),
 * Compose vuelve a ejecutar la función y la pantalla se actualiza sola.
 *
 * Esta pantalla NO sabe cómo se abre la siguiente. Solo recibe una función
 * ("onLoginCorrecto") y la llama cuando el usuario acierta, entregándole el
 * nombre. Quien la use (MainActivity) decide qué hacer con él.
 */
@Composable
fun LoginScreen(
    onLoginCorrecto: (nombre: String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = viewModel(),
) {

    // ---------- ESTADO ----------
    var email = viewModel.email
    var password = viewModel.password
    var passwordVisible = viewModel.passwordVisible
    var hayError = viewModel.hayError  // ¿mostramos el aviso de error?


    // ---------- TEMBLOR AL FALLAR ----------
    // Animatable guarda un número que podemos animar (cuántos dp se mueve el formulario)
    val temblor = remember { Animatable(0f) }
    // Se ejecuta cada vez que cambia intentosFallidos: izquierda-derecha hasta volver a 0
    LaunchedEffect(viewModel.intentosFallidos) {
        if (viewModel.intentosFallidos > 0) {
            for (posicion in listOf(-14f, 14f, -9f, 9f, -4f, 0f)) {
                temblor.animateTo(posicion, animationSpec = tween(durationMillis = 50))
            }
        }
    }

    // ---------- DISEÑO ----------
    // Column = apila los elementos en vertical (como un Auto Layout vertical en Figma).
    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .imePadding(),
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .heightIn(min = maxHeight)
                .padding(horizontal = 24.dp),
        ) {
        Spacer(Modifier.height(40.dp))     // Spacer = espacio vacío

        // --- Cabecera: logo + título + subtítulo ---
        LogoCircular(icono = R.drawable.ic_lock, tamanoIcono = 32.dp)
        Spacer(Modifier.height(24.dp))
        Text(
            text = stringResource(R.string.login_titulo),
            style = MaterialTheme.typography.headlineMedium,   // estilo del tema, no tamaños sueltos
            color = MaterialTheme.colorScheme.onSurface,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.login_subtitulo),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(Modifier.height(32.dp))

        // Column que agrupa los dos campos para que tiemblen juntos
        Column(modifier = Modifier.offset(x = temblor.value.dp))
        {

            // --- Campo de correo ---
            // OutlinedTextField es el campo de texto con borde de Material 3: ya trae
            // la etiqueta flotante y el borde morado al enfocar. "isError" lo pone rojo.
            OutlinedTextField(
                value = email,
                onValueChange = { viewModel.onEmailChange(it) }, // viewmodel
                label = { Text(stringResource(R.string.campo_correo)) },
                singleLine = true,
                isError = hayError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(Modifier.height(16.dp))

            // --- Campo de contraseña ---
            OutlinedTextField(
                value = password,
                onValueChange = { viewModel.onPasswordChange(it) }, // viewmodel
                label = { Text(stringResource(R.string.campo_contrasena)) },
                singleLine = true,
                isError = hayError,
                // "supportingText" es el texto pequeño bajo el campo: solo lo mostramos si hay error.
                supportingText = { if (hayError) Text(stringResource(R.string.error_login)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                // Si la contraseña es visible mostramos el texto tal cual; si no, puntos.
                visualTransformation = if (passwordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                // Icono del ojo a la derecha: al pulsarlo cambia el estado.
                trailingIcon = {
                    IconButton(onClick = { viewModel.cambiarVisibilidad() }) { // viewmodel
                        Icon(
                            painter = painterResource(
                                if (passwordVisible) R.drawable.ic_visibility_off else R.drawable.ic_visibility
                            ),
                            contentDescription = if (passwordVisible) stringResource(R.string.ocultar_contrasena) else stringResource(R.string.mostrar_contrasena),
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
            )
        } // fin de la Column que tiembla

        Spacer(Modifier.height(8.dp))

        // --- "¿Olvidaste tu contraseña?" alineado a la derecha ---
        // Row = apila en horizontal. Arrangement.End lo empuja al final (derecha).
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
        ) {
            TextButton(onClick = { /* pendiente: recuperar contraseña */ }) {
                Text(stringResource(R.string.olvidaste_contrasena))
            }
        }

        Spacer(Modifier.height(16.dp))

        // --- Botón principal: LÓGICA DEL LOGIN ---
        Button( //viewmodel
            onClick = {
                val nombre = viewModel.iniciarSesion()
                if (nombre != null) {
                    onLoginCorrecto(nombre)
                }
            },
            enabled = email.isNotBlank() && password.isNotBlank(),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(stringResource(R.string.boton_iniciar_sesion))
        }

        Spacer(Modifier.height(24.dp))

        // --- Divisor con la letra "o": línea — o — línea ---
        Row(verticalAlignment = Alignment.CenterVertically) {
            // weight(1f) reparte el ancho: cada línea ocupa la mitad de lo que sobra.
            HorizontalDivider(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(R.string.separador_o),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 16.dp),
            )
            HorizontalDivider(modifier = Modifier.weight(1f))
        }

        Spacer(Modifier.height(24.dp))

        // --- Botón con contorno ---
        OutlinedButton(
            onClick = { /* pendiente: iniciar sesión con Google */ },
            modifier = Modifier.fillMaxWidth(),
            // Texto en el color primario (lila), como en el diseño de Figma.
            colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.primary),
        ) {
            Text(stringResource(R.string.continuar_google))
        }

        // Este Spacer "elástico" ocupa TODO el espacio libre que sobra,
        // y así el pie queda pegado abajo (como el "Espacio flexible" de Figma).
        Spacer(Modifier.weight(1f))

        // --- Pie: "¿No tienes cuenta? Regístrate" ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.no_tienes_cuenta),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            TextButton(onClick = { /* pendiente: pantalla de registro */ }) {
                Text(stringResource(R.string.registrate))
            }
        }

        Spacer(Modifier.height(16.dp))
    }
    }
}

// Vista previa en Android Studio, con el tamaño exacto del Samsung Galaxy S24 (360 x 780 dp).
@Preview(name = "Galaxy S24", showBackground = true, widthDp = 360, heightDp = 780)
@Composable
private fun LoginScreenPreview() {
    Login_composeTheme {
        // En la app real el color de fondo lo pone el Scaffold; aquí usamos Surface.
        Surface(color = MaterialTheme.colorScheme.background) {
            LoginScreen(onLoginCorrecto = {})
        }
    }
}