package com.maria.login_compose.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

// ---------- DATOS DE PRUEBA (hardcodeados) ----------
private const val CORREO_CORRECTO = "montesm.mail@gmail.com"
private const val CLAVE_CORRECTA = "1234"
private const val NOMBRE_USUARIO = "María"

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