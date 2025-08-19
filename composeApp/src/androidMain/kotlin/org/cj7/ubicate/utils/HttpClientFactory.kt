package org.cj7.ubicate.utils

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp

actual class HttpClientFactory actual constructor() {
    actual fun create(): HttpClient {
        return HttpClient(OkHttp) {
            // Configuración específica de Android
        }
    }
}
