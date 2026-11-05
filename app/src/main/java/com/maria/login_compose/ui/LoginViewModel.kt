package com.maria.login_compose.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlin.coroutines.EmptyCoroutineContext.get

// ---------- DATOS DE PRUEBA (hardcodeados) ----------
private const val CORREO_CORRECTO = "prueba@gmail.com"
private const val CLAVE_CORRECTA = "prueba12"  // ahora la contraseña debe ser igual o mayor a 8 caracteres
private const val NOMBRE_USUARIO = "Manolo"

private const val MINIMO_CARACTERES = 8   // longitud mínima de la contraseña

class LoginViewModel : ViewModel() {

    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    var passwordVisible by mutableStateOf(false)
        private set
    var hayError by mutableStateOf(false)
        private set

    // Cuenta los fallos; cada vez que sube, la pantalla hace temblar el formulario
    var intentosFallidos by mutableStateOf(0)
        private set

    fun onEmailChange(nuevo: String) {
        email = nuevo
        hayError = false
    }

    fun onPasswordChange(nueva: String) {
        password = nueva
        hayError = false
    }

    fun cambiarVisibilidad() {
        passwordVisible = !passwordVisible
    }

    // El botón solo se activa si hay correo y la contraseña tiene al menos 8 caracteres.
    // Como email y password son "estado", Compose lo vuelve a comprobar cada vez que escribes.
    val puedeIniciarSesion: Boolean
        get() = email.isNotBlank() && password.length >= MINIMO_CARACTERES

    fun iniciarSesion(): String? {
        return if (email.trim() == CORREO_CORRECTO && password == CLAVE_CORRECTA) {
            limpiar()
            NOMBRE_USUARIO
        } else {
            hayError = true
            intentosFallidos++
            null
        }
    }

    private fun limpiar() {
        email = ""
        password = ""
        passwordVisible = false
        hayError = false
        intentosFallidos = 0
    }
}