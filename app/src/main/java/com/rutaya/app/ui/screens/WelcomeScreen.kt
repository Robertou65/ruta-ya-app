package com.rutaya.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rutaya.app.ui.components.RutaYaLogo

@Composable
fun WelcomeScreen(onContinue: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background).padding(28.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        RutaYaLogo()
        Spacer(Modifier.height(24.dp))
        Text("RutaYa", fontSize = 42.sp, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.primary)
        Text("Entregas en el camino correcto", fontSize = 18.sp, fontWeight = FontWeight.Medium)
        Spacer(Modifier.height(18.dp))
        Text(
            "Organiza tus pedidos y prepárate para visualizar cada entrega en una sola ruta.",
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = .72f)
        )
        Spacer(Modifier.height(42.dp))
        Button(onClick = onContinue, modifier = Modifier.fillMaxWidth().height(54.dp)) {
            Text("Continuar", fontSize = 17.sp)
        }
    }
}
