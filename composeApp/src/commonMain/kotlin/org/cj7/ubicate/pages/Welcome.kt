package com.cj7.lojapp.pages

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import ubicate.composeapp.generated.resources.Res
import ubicate.composeapp.generated.resources.logoubicatesinfondo
import ubicate.composeapp.generated.resources.ubicatefondo

@Composable
fun WelcomeScreen(navigateToHome: () -> Unit) {
    // Animación de escala
    val infiniteTransition = rememberInfiniteTransition(label = "CargandoAnimacion")
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "PulsatingScale"
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Fondo
        Image(
            painter = painterResource(Res.drawable.ubicatefondo),
            contentDescription = "Fondo Loja",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        // Logo animado
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Image(
                painter = painterResource(Res.drawable.logoubicatesinfondo),
                contentDescription = "Logo animado",
                modifier = Modifier
                    .size(300.dp)
                    .scale(scale)
            )
        }
        // Redirección con delay
        LaunchedEffect(Unit) {
            delay(1100)
            navigateToHome()
        }
    }
}
