package org.cj7.ubicate.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil3.compose.AsyncImage

@Composable
fun ImagenesSlider(
    imagenes: List<String>,
    titulo: String = "Cafetería"
) {
    var imagenSeleccionada by remember { mutableStateOf<String?>(null) }
    var indexSeleccionado by remember { mutableStateOf(0) }

    Column {
        Text(
            text = titulo,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(imagenes) { imagenUrl ->
                AsyncImage(
                    model = imagenUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(250.dp)
                        .height(180.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable {
                            imagenSeleccionada = imagenUrl
                            indexSeleccionado = imagenes.indexOf(imagenUrl)
                        }
                )
            }
        }
    }

    // Dialog con zoom y navegación
    imagenSeleccionada?.let {
        Dialog(
            onDismissRequest = { imagenSeleccionada = null },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.8f)),
                contentAlignment = Alignment.Center
            ) {
                var scale by remember { mutableStateOf(1f) }
                var offset by remember { mutableStateOf(Offset.Zero) }

                val state = rememberTransformableState { zoomChange, offsetChange, _ ->
                    scale = (scale * zoomChange).coerceIn(1f, 5f)
                    offset += offsetChange
                }

                Box(
                    modifier = Modifier
                        .graphicsLayer(
                            scaleX = scale,
                            scaleY = scale,
                            translationX = offset.x,
                            translationY = offset.y
                        )
                        .transformable(state = state)
                ) {
                    AsyncImage(
                        model = imagenes[indexSeleccionado],
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )
                }

                // Flechas
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 32.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (indexSeleccionado > 0) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .background(Color(0xFF3F51B5), RoundedCornerShape(12.dp))
                                .clickable { indexSeleccionado-- },
                            contentAlignment = Alignment.Center
                        ) {
                            Text("<", fontSize = 32.sp, color = Color.White)
                        }
                    }
                    if (indexSeleccionado < imagenes.lastIndex) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .background(Color(0xFF3F51B5), RoundedCornerShape(12.dp))
                                .clickable { indexSeleccionado++ },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(">", fontSize = 32.sp, color = Color.White)
                        }
                    }
                }
            }
        }
    }
}