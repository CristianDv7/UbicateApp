package org.cj7.ubicate

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform