package org.cj7.ubicate.utils

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin

actual class HttpClientFactory actual constructor() {
    actual fun create(): HttpClient {
        return HttpClient(Darwin) {
            // Configuración específica de iOS
        }
    }
}
