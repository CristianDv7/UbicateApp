package org.cj7.ubicate.funciones

import io.ktor.client.call.*
import io.ktor.client.request.*
import org.cj7.ubicate.model.LocalesUbicate
import org.cj7.ubicate.network.NetworkUtils
import org.cj7.ubicate.network.SupabaseConfig

suspend fun getLocales(nombre: String): List<LocalesUbicate> {
    if (nombre.isBlank()) return emptyList()

    return NetworkUtils.httpClient.get("${SupabaseConfig.SUPABASE_URL}/rest/v1/locales") {
        headers {
            append("apikey", SupabaseConfig.SUPABASE_KEY)
            append("Authorization", "Bearer ${SupabaseConfig.SUPABASE_KEY}")
        }
        // filtramos por nombre con operador ilike (case insensitive)
        parameter("nombre", "ilike.%$nombre%")
        parameter("select", "*")
    }.body()
}