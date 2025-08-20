package org.cj7.ubicate.network


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.cj7.ubicate.model.LocalesUbicate
import org.cj7.ubicate.network.SupabaseConfig
import org.cj7.ubicate.network.NetworkUtils
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.utils.io.core.toByteArray
import kotlinx.coroutines.IO
import okio.ByteString.Companion.encodeUtf8


// 🔹 Calcula hash único por cada Local
fun LocalesUbicate.calcularHash(): String {
    val data = listOf(
        id, nombre, imagen, descripcion, direccion, categoria, latitud,
        longitud, ahorita, horario, contador, whatsapp, instagram, tiktok,
        facebook, imagenes1, imagenes2, suscripcion
    ).joinToString("|")

    return data.encodeUtf8().sha256().hex()
}

// 🔹 Interfaz multiplataforma para DB local
interface LocalDB {
    suspend fun obtenerTodosLosIdsLocales(): Set<Int>
    suspend fun eliminarDetalleLocalPorId(id: Int)
    suspend fun obtenerHashLocalPorId(id: Int): String?
    suspend fun insertarDetalleLocal(local: LocalesUbicate)
}

// 🔹 Sincronizar datos locales con Supabase
suspend fun sincronizarTodosLosLocales(
    localDB: LocalDB,
    onProgress: (Float) -> Unit
) {
    try {
        // 1. Descargar locales desde Supabase
        val locales = withContext(Dispatchers.IO) {
            NetworkUtils.httpClient.get("${SupabaseConfig.SUPABASE_URL}/rest/v1/locales") {
                headers {
                    append("apikey", SupabaseConfig.SUPABASE_KEY)
                    append("Authorization", "Bearer ${SupabaseConfig.SUPABASE_KEY}")
                }
                parameter("select", "*")
            }.body<List<LocalesUbicate>>()
        }

        val idsSupabase = locales.map { it.id }.toSet()

        // 2. Obtener IDs locales en la DB local
        val idsLocalesDB = localDB.obtenerTodosLosIdsLocales()

        // 3. Eliminar registros que ya no existen en Supabase
        val idsEliminar = idsLocalesDB - idsSupabase
        for (id in idsEliminar) {
            localDB.eliminarDetalleLocalPorId(id)
        }

        // 4. Insertar / Actualizar registros con progreso
        val total = locales.size
        locales.forEachIndexed { index, local ->
            val hashActual = local.calcularHash()
            val hashGuardado = localDB.obtenerHashLocalPorId(local.id)

            if (hashActual != hashGuardado) {
                localDB.insertarDetalleLocal(local)
            }

            val progreso = (index + 1) / total.toFloat()
            onProgress(progreso)
        }
    } catch (e: Exception) {
        println("Error al sincronizar: ${e.message}")
        onProgress(1f)
    }
}
