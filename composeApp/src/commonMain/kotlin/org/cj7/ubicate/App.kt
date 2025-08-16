package org.cj7.ubicate

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.cj7.lojapp.pages.WelcomeScreen
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App() {
    MaterialTheme {
        Box(Modifier.statusBarsPadding()){
            Text("Hola Mundo")
            WelcomeScreen(navigateToHome = {})
        }
    }
}