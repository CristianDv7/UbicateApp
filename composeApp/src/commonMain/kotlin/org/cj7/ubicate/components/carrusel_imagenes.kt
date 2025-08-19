package org.cj7.ubicate.components

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil3.compose.AsyncImage
import kotlinx.coroutines.delay
import org.cj7.ubicate.model.Eventos

@Composable
fun ImageCarousel(eventos: List<Eventos>) {
    var selectedIndex by remember { mutableStateOf<Int?>(null) }
    val pagerState = rememberPagerState(pageCount = { eventos.size })

    // autoplay
    LaunchedEffect(pagerState, eventos) {
        while (eventos.isNotEmpty()) {
            delay(6000)
            val nextPage = (pagerState.currentPage + 1) % eventos.size
            pagerState.animateScrollToPage(nextPage)
        }
    }

    if (eventos.isNotEmpty()) {
        Column(
            modifier = Modifier
                .background(Color.Transparent, shape = RoundedCornerShape(8.dp))
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
            ) { page ->
                EventoCard(evento = eventos[page]) {
                    selectedIndex = page
                }
            }

            // Indicadores simples
            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(eventos.size) { index ->
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .padding(2.dp)
                            .background(
                                if (pagerState.currentPage == index) Color.Blue else Color.Gray,
                                RoundedCornerShape(50)
                            )
                    )
                }
            }
        }

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
            contentAlignment = Alignment.Center
        ) {
            Text("Cargando...")
        }
    }
}

@Composable
fun EventoCard(evento: Eventos, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .border(1.dp, color = Color(0xFF3F51B5), RoundedCornerShape(16.dp))
            .fillMaxSize()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            // Descripción
            Text(
                text = evento.descripcion,
                modifier = Modifier
                    .weight(0.4f)
                    .padding(4.dp)
                    .align(Alignment.CenterVertically),
                color = Color.Black,
                fontSize = 14.sp
            )

            // Imagen (si hay)
            if (!evento.imagen.isNullOrEmpty()) {
                AsyncImage(
                    model = evento.imagen,
                    contentDescription = "Imagen del evento",
                    modifier = Modifier
                        .weight(0.6f)
                        .fillMaxHeight(),
                    contentScale = ContentScale.Crop
                )
            }
        }

        // Play superpuesto si hay video
        if (!evento.video.isNullOrEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "▶",
                    color = Color.Red,
                    fontSize = 38.sp,
                    modifier = Modifier
                        .background(Color(0x66000000), RoundedCornerShape(6.dp))
                        .padding(8.dp)
                )
            }
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
            when {
                !evento.imagen.isNullOrEmpty() -> {
                    AsyncImage(
                        model = evento.imagen,
                        contentDescription = "Imagen completa",
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .clickable { onDismiss() }
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
                            ),
                        contentScale = ContentScale.Fit
                    )
                }

                else -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(Color.Gray),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Sin contenido", color = Color.White)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (currentIndex > 0) {
                    Text(
                        "<",
                        fontSize = 32.sp,
                        color = Color.White,
                        modifier = Modifier
                            .background(Color(0xFF3F51B5), RoundedCornerShape(12.dp))
                            .padding(8.dp)
                            .clickable { onNavigate(currentIndex - 1) }
                    )
                }

                if (currentIndex < eventos.lastIndex) {
                    Text(
                        ">",
                        fontSize = 32.sp,
                        color = Color.White,
                        modifier = Modifier
                            .background(Color(0xFF3F51B5), RoundedCornerShape(12.dp))
                            .padding(8.dp)
                            .clickable { onNavigate(currentIndex + 1) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 100.dp, max = 300.dp)
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .verticalScroll(rememberScrollState())
                    .padding(12.dp)
                    .clickable { onDismiss() }
            ) {
                Text(
                    text = evento.descripcion,
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }
        }
    }
}
