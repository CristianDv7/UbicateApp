package org.cj7.ubicate.components


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.serialization.Serializable
import org.cj7.ubicate.network.NetworkUtils
import org.cj7.ubicate.network.SupabaseConfig

// --- DATA CLASS ---
@Serializable
data class Local(
    val id: Int,
    val nombre: String,
    val imagen: String,
    val descripcion: String,
    val direccion: String,
    val categoria: String,
    val latitud: String,
    val longitud: String,
    val suscripcion: Int = 0,
    val etiquetas: String? = null
)

// --- REPOSITORIO ---
class LocalesRepository(
    private val client: io.ktor.client.HttpClient,
    private val supabaseUrl: String,
    private val supabaseKey: String
) {

    suspend fun fetchLocales(): List<Local> {
        return client.get("${SupabaseConfig.SUPABASE_URL}/rest/v1/locales") {
            headers {
                append("apikey", SupabaseConfig.SUPABASE_KEY)
                append("Authorization", "Bearer ${SupabaseConfig.SUPABASE_KEY}")
            }
        }.body()
    }

    fun filterLocales(allLocales: List<Local>, searchQuery: String): List<Local> {
        if (searchQuery.isBlank() || searchQuery.equals("Todos", ignoreCase = true)) {
            return allLocales
        }

        return allLocales.filter { local ->
            val etiquetas = local.etiquetas?.split(",")?.map { it.trim().lowercase() } ?: emptyList()
            local.nombre.contains(searchQuery, ignoreCase = true) ||
                    local.categoria.equals(searchQuery, ignoreCase = true) ||
                    etiquetas.any { it.contains(searchQuery.lowercase()) }
        }
    }
}

// --- COMPOSABLE PRINCIPAL ---
@Composable
fun LocalesView(
    modifier: Modifier = Modifier,
    searchQuery: String,
    navigatetoLocalContent: (String) -> Unit
) {
    val repository = remember { LocalesRepository(NetworkUtils.httpClient, "TU_SUPABASE_URL", "TU_SUPABASE_KEY") }

    var allLocales by remember { mutableStateOf<List<Local>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        allLocales = try {
            repository.fetchLocales()
        } catch (e: Exception) {
            emptyList()
        }
        isLoading = false
    }

    val filteredLocales by remember(searchQuery, allLocales) {
        derivedStateOf { repository.filterLocales(allLocales, searchQuery) }
    }

    if (isLoading) {
        Box(modifier = modifier.fillMaxWidth(), contentAlignment = androidx.compose.ui.Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(modifier = modifier.fillMaxWidth()) {
            items(filteredLocales) { local ->
                val cardColor = if (local.suscripcion == 1) {
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                } else {
                    MaterialTheme.colorScheme.surface
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clickable { navigatetoLocalContent(local.nombre) },
                    elevation = CardDefaults.cardElevation(4.dp),
                    colors = CardDefaults.cardColors(containerColor = cardColor)
                ) {
                    LocalCardContent(local = local, searchQuery = searchQuery)
                }
            }
        }
    }
}

// --- CARD CONTENT ---
@Composable
fun LocalCardContent(local: Local, searchQuery: String) {
    if (local.suscripcion == 2) return // No mostrar si es suscripción 2

    Row(modifier = Modifier.padding(16.dp)) {
        AsyncImage(
            model = local.imagen,
            contentDescription = "Imagen de ${local.nombre}",
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Row {
                Text(local.nombre, style = MaterialTheme.typography.titleMedium)
                if (local.suscripcion == 1) {
                    Text(" 👑", style = MaterialTheme.typography.titleMedium)
                }
            }
            Text("📍 Categoría: ${local.categoria}", style = MaterialTheme.typography.bodyMedium)

            // Mostrar coincidencias en etiquetas
            val etiquetasFiltradas = local.etiquetas
                ?.split(",")
                ?.map { it.trim() }
                ?.filter { it.contains(searchQuery, ignoreCase = true) }
                ?: emptyList()

            etiquetasFiltradas.forEach { etiqueta ->
                Text("Coincidencia: 🔥$etiqueta🔥", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
