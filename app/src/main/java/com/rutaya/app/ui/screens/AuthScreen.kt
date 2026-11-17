package com.rutaya.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.rutaya.app.data.Driver

@Composable
fun AuthScreen(
    onLogin: (String, String) -> Driver?,
    onRegister: (Driver, String) -> Result<Driver>
) {
    var registering by rememberSaveable { mutableStateOf(false) }
    Column(Modifier.fillMaxSize().padding(top = 52.dp)) {
        Text(
            if (registering) "Crea tu cuenta" else "Bienvenido de nuevo",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
        Text(
            if (registering) "Completa tus datos como repartidor" else "Ingresa para consultar tu perfil",
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = .65f),
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 6.dp)
        )
        TabRow(selectedTabIndex = if (registering) 1 else 0) {
            Tab(!registering, { registering = false }, text = { Text("Ingresar") })
            Tab(registering, { registering = true }, text = { Text("Registrarme") })
        }
        if (registering) RegisterForm(onRegister) else LoginForm(onLogin)
    }
}

@Composable
private fun LoginForm(onLogin: (String, String) -> Driver?) {
    var document by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var error by rememberSaveable { mutableStateOf<String?>(null) }
    Column(Modifier.fillMaxWidth().padding(24.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        AppField(document, { document = it }, "Número de documento", KeyboardType.Number)
        AppField(password, { password = it }, "Contraseña", password = true)
        error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        Button(
            onClick = {
                error = when {
                    document.isBlank() || password.isBlank() -> "Completa todos los campos."
                    onLogin(document, password) == null -> "Documento o contraseña incorrectos."
                    else -> null
                }
            },
            modifier = Modifier.fillMaxWidth().height(52.dp)
        ) { Text("Ingresar") }
    }
}

@Composable
private fun RegisterForm(onRegister: (Driver, String) -> Result<Driver>) {
    var name by rememberSaveable { mutableStateOf("") }
    var documentType by rememberSaveable { mutableStateOf("Cédula de ciudadanía") }
    var document by rememberSaveable { mutableStateOf("") }
    var vehicleType by rememberSaveable { mutableStateOf("Motocicleta") }
    var plate by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmation by rememberSaveable { mutableStateOf("") }
    var acceptsLocalStorage by rememberSaveable { mutableStateOf(false) }
    var error by rememberSaveable { mutableStateOf<String?>(null) }

    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        AppField(name, { name = it }, "Nombre completo")
        ChoiceField("Tipo de documento", documentType, listOf("Cédula de ciudadanía", "Cédula de extranjería", "Pasaporte")) { documentType = it }
        AppField(document, { document = it }, "Número de documento", KeyboardType.Number)
        ChoiceField("Tipo de vehículo", vehicleType, listOf("Bicicleta", "Motocicleta", "Automóvil", "Furgón")) { vehicleType = it }
        AppField(plate, { plate = it.uppercase() }, "Placa del vehículo")
        AppField(password, { password = it }, "Contraseña", password = true)
        AppField(confirmation, { confirmation = it }, "Confirmar contraseña", password = true)
        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
            Checkbox(checked = acceptsLocalStorage, onCheckedChange = { acceptsLocalStorage = it })
            Text("Acepto guardar mis datos localmente en este dispositivo")
        }
        error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        Button(
            onClick = {
                error = when {
                    listOf(name, document, plate, password, confirmation).any { it.isBlank() } -> "Completa todos los campos."
                    name.trim().length < 3 -> "Ingresa tu nombre completo."
                    password.length < 6 -> "La contraseña debe tener al menos 6 caracteres."
                    password != confirmation -> "Las contraseñas no coinciden."
                    !acceptsLocalStorage -> "Debes aceptar el almacenamiento local de tus datos."
                    else -> onRegister(Driver(name, documentType, document, vehicleType, plate), password)
                        .exceptionOrNull()?.message
                }
            },
            modifier = Modifier.fillMaxWidth().height(52.dp)
        ) { Text("Crear cuenta") }
        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun AppField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    password: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = if (password) PasswordVisualTransformation() else androidx.compose.ui.text.input.VisualTransformation.None,
        modifier = Modifier.fillMaxWidth()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChoiceField(label: String, value: String, choices: List<String>, onChoice: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(expanded, { expanded = !expanded }) {
        OutlinedTextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            modifier = Modifier.menuAnchor().fillMaxWidth()
        )
        ExposedDropdownMenu(expanded, { expanded = false }) {
            choices.forEach { choice ->
                DropdownMenuItem(text = { Text(choice) }, onClick = { onChoice(choice); expanded = false })
            }
        }
    }
}
