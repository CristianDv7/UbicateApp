package org.cj7.ubicate.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil3.compose.AsyncImage
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.delay
import kotlinx.serialization.Serializable
import org.cj7.ubicate.network.NetworkUtils

@Serializable
data class Eventos(
    val id: Int,
    val imagen: String?,
    val descripcion: String?
)

class EventosRepository(
    private val client: io.ktor.client.HttpClient,
    private val supabaseUrl: String,
    private val supabaseKey: String
) {
    suspend fun fetchEventos(): List<Eventos> {
        return client.get("$supabaseUrl/rest/v1/eventos") {
            headers {
                append("apikey", supabaseKey)
                append("Authorization", "Bearer $supabaseKey")
            }
        }.body()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageCarousel() {
    val repository = remember { EventosRepository(NetworkUtils.httpClient, "TU_SUPABASE_URL", "TU_SUPABASE_KEY") }
    var eventos by remember { mutableStateOf<List<Eventos>>(emptyList()) }
    var selectedIndex by remember { mutableStateOf<Int?>(null) }

    LaunchedEffect(Unit) {
        eventos = try {
            repository.fetchEventos()
        } catch (e: Exception) {
            emptyList()
        }
    }

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { eventos.size }
    )

    // Cambio automático de página
    LaunchedEffect(eventos) {
        while (true) {
            delay(6000)
            if (eventos.isNotEmpty()) {
                val nextPage = (pagerState.currentPage + 1) % eventos.size
                pagerState.animateScrollToPage(nextPage)
            }
        }
    }

    if (eventos.isNotEmpty()) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // --- CARRUSEL ---
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
            ) { page ->
                EventoCard(evento = eventos[page]) { selectedIndex = page }
            }

            // --- INDICADOR DE PÁGINAS ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                eventos.forEachIndexed { index, _ ->
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .padding(2.dp)
                            .clip(RoundedCornerShape(50))
                            .background(if (pagerState.currentPage == index) Color(172, 180, 251) else Color.Gray)
                    )
                }
            }
        }

        // --- FULL SCREEN DIALOG ---
        selectedIndex?.let { index ->
            FullScreenImageDialog(
                eventos = eventos,
                currentIndex = index,
                onDismiss = { selectedIndex = null },
                onNavigate = { newIndex -> selectedIndex = newIndex }
            )
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            Text("Cargando...")
        }
    }
}

@Composable
fun EventoCard(evento: Eventos, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .background(Color.LightGray)
            .fillMaxSize()
    ) {
        evento.imagen?.let {
            AsyncImage(
                model = it,
                contentDescription = "Imagen del evento",
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun FullScreenImageDialog(
    eventos: List<Eventos>,
    currentIndex: Int,
    onDismiss: () -> Unit,
    onNavigate: (Int) -> Unit
) {
    val evento = eventos[currentIndex]
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
                .padding(16.dp)
        ) {
            evento.imagen?.let { url ->
                AsyncImage(
                    model = url,
                    contentDescription = "Imagen completa",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp) // Limitamos la altura para evitar el crash
                        .pointerInput(Unit) {
                            detectTransformGestures { _, pan, zoom, _ ->
                                scale = (scale * zoom).coerceIn(1f, 3f)
                                offset += pan
                            }
                        }
                        .graphicsLayer(
                            scaleX = scale,
                            scaleY = scale,
                            translationX = offset.x,
                            translationY = offset.y
                        )
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                if (currentIndex > 0) {
                    Text("<", fontSize = 32.sp, color = Color.White, modifier = Modifier.clickable { onNavigate(currentIndex - 1) })
                }
                if (currentIndex < eventos.lastIndex) {
                    Text(">", fontSize = 32.sp, color = Color.White, modifier = Modifier.clickable { onNavigate(currentIndex + 1) })
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Descripción en LazyColumn para scroll seguro
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 100.dp, max = 200.dp)
                    .background(Color.White, RoundedCornerShape(8.dp))
                    .padding(12.dp)
            ) {
                items(listOf(evento.descripcion ?: "")) { desc ->
                    Text(
                        text = desc,
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                }
            }
        }
    }
}
