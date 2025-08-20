package org.cj7.ubicate.model

import kotlinx.serialization.Serializable

// Datos para LocalContent
@Serializable
data class LocalesUbicate(
    val id: Int,
    val nombre: String,
    val imagen: String,
    val descripcion: String,
    val direccion: String,
    val categoria: String,
    val latitud: String,
    val longitud: String,
    val ahorita: String,
    val horario: String,
    val contador: Int,
    val whatsapp: String,
    val instagram: String,
    val tiktok: String,
    val facebook: String,
    val imagenes1: String = "",
    val imagenes2: String = "",
    val suscripcion: Int = 0,


    )

// Datos para Servicios
@Serializable
data class ServiciosData(
    val nombre: String,
    val imagen: String,
    val imagen2: String? = null,
    val imagen3: String? = null,
    val descripcion: String,
    val whatsapp: String,
    val instagram: String,
    val facebook: String,
    val tiktok: String,
    val valoracion_total: Int = 0,
    val valoracion_count: Int = 0
)

//Carrusel de imagenes

// Define el modelo de datos
@Serializable
data class Eventos(
    val id: Int,
    val imagen: String,
    val descripcion: String,
    val video: String? = null
)

//LocalGPS
@Serializable
data class Location(
    val latitud: String,
    val longitud: String,
    val nombre: String, // Nombre del local
    val imagen: String,
    val categoria: String,
    val etiquetas: String
)
