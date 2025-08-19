package org.cj7.ubicate.utils

import io.ktor.client.HttpClient

expect class HttpClientFactory() {
    fun create(): HttpClient
}
