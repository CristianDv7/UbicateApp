package org.cj7.ubicate

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.cj7.lojapp.pages.WelcomeScreen
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.cj7.ubicate.funciones.getLocales
import org.cj7.ubicate.model.LocalesUbicate
import org.cj7.ubicate.network.NetworkUtils.httpClient
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App() {
    MaterialTheme {
        var nombreBusqueda by remember { mutableStateOf("") }
        var localesList by remember{ mutableStateOf<List<LocalesUbicate>>(emptyList()) }

        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Row {
                TextField(value = nombreBusqueda, onValueChange = { nombreBusqueda = it })
                Button(onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        val result = getLocales(nombreBusqueda)
                        withContext(Dispatchers.Main) {
                            localesList = result
                        }
                    }
                }) {
                    Text("Buscar")
                }
            }
            //List
            LazyColumn {
                items(localesList){ local ->
                    Text(local.nombre)
                }
            }
        }
    }
}

