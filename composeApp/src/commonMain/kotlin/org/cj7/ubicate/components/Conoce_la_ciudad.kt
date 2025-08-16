package org.cj7.ubicate.components


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage


data class OpcionLoja(
    val nombre: String,
    val imagenUrl: String, // URL de Supabase
    val tipo: String
)

@Composable
fun DisfrutaDeLoja(
    navigatetolocalGPS: (String) -> Unit,
    opciones: List<OpcionLoja>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
    ) {
        LazyRow(
            contentPadding = PaddingValues(horizontal = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(opciones) { opcion ->
                Column(
                    modifier = Modifier
                        .width(120.dp)
                        .clickable { navigatetolocalGPS(opcion.tipo) },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = opcion.imagenUrl,
                        contentDescription = opcion.nombre,
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = opcion.nombre,
                        color = MaterialTheme.colorScheme.secondary,
                        textAlign = TextAlign.Center,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}
