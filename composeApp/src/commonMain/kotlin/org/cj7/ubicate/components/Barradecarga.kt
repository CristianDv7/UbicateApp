package org.cj7.ubicate.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import ubicate.composeapp.generated.resources.Res
import ubicate.composeapp.generated.resources.logoubicatesinfondo


@Composable
fun SyncConfirmationDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("¿Deseas descargar los locales?") },
        text = { Text("Esta descarga te permitirá usar la app sin conexión.") },
        confirmButton = {
            TextButton(onClick = onConfirm) { Text("Sí, descargar") }
        },
        dismissButton = {
            Button(onClick = onDismiss) { Text("No, continuar sin descargar") }
        }
    )
}

@Composable
fun LoadingScreen(isLoading: Boolean, progress: Float) {
    if (isLoading) {
        val infiniteTransition = rememberInfiniteTransition()
        val scale by infiniteTransition.animateFloat(
            initialValue = 1f,
            targetValue = 1.1f,
            animationSpec = infiniteRepeatable(
                animation = tween(1000, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            )
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(63, 81, 181)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "BIENVENIDO A UBÍCATE",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White
                )

                Image(
                    painter = painterResource(Res.drawable.logoubicatesinfondo),
                    contentDescription = "Logo cargando",
                    modifier = Modifier
                        .size(200.dp)
                        .scale(scale)
                )

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Cargando locales...",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    LinearProgressIndicator(
                        progress = progress,
                        color = Color.White,
                        trackColor = Color(63, 81, 181),
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp))
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "${(progress * 100).toInt()}%",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Esta descarga se realiza solo una vez. Luego podrás usar la app con o sin conexión.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White,
                        modifier = Modifier.padding(top = 12.dp),
                        maxLines = 3
                    )
                }
            }
        }
    }
}
