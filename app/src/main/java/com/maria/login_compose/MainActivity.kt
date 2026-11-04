package com.maria.login_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.maria.login_compose.ui.LoginScreen
import com.maria.login_compose.ui.WelcomeScreen
import com.maria.login_compose.ui.theme.Login_composeTheme
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // La app se dibuja bajo la barra de estado y la de navegación (pantalla completa).
        enableEdgeToEdge()
        // setContent: aquí empieza Compose. Todo lo de dentro es interfaz declarativa.
        setContent {
            val sistemaEnOscuro = isSystemInDarkTheme()
            var modoOscuro by rememberSaveable { mutableStateOf(sistemaEnOscuro) }

            Login_composeTheme(darkTheme = modoOscuro) {
                App(
                    modoOscuro = modoOscuro,
                    onCambiarModo = { modoOscuro = !modoOscuro },
                )
            }
        }
    }
}

/**
 * Decide QUÉ pantalla se ve, usando un único estado: el nombre del usuario.
 *
 *   nombreUsuario == null   ->  nadie ha iniciado sesión  ->  mostramos el LOGIN
 *   nombreUsuario != null   ->  sesión iniciada           ->  mostramos la BIENVENIDA
 *
 * Cuando el login avisa de que el usuario acertó, guardamos su nombre en el
 * estado; Compose redibuja y cambia de pantalla solo. Al cerrar sesión hacemos
 * lo contrario: volvemos a poner el estado en null. No hace falta ninguna
 * librería de navegación para este ejemplo.
 *
 * ¿Por qué el formulario vuelve LIMPIO? Porque al cambiar a la bienvenida la
 * pantalla de login desaparece de la composición y su estado (lo que se había
 * escrito) se descarta. Al volver, Compose la crea de cero y los campos están vacíos.
 */

@Composable
private fun App(
    modoOscuro: Boolean,
    onCambiarModo: () -> Unit,
) {
    var nombreUsuario by rememberSaveable { mutableStateOf<String?>(null) }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            val nombre = nombreUsuario

            if (nombre == null) {
                LoginScreen(
                    onLoginCorrecto = { nombreUsuario = it },
                )
            } else {
                WelcomeScreen(
                    nombre = nombre,
                    onCerrarSesion = { nombreUsuario = null },
                )
            }

            IconButton(
                onClick = onCambiarModo,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp),
            ) {
                Icon(
                    painter = painterResource(
                        if (modoOscuro) R.drawable.ic_light_mode else R.drawable.ic_dark_mode
                    ),
                    contentDescription = if (modoOscuro) stringResource(R.string.modo_claro) else stringResource(R.string.modo_oscuro),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}