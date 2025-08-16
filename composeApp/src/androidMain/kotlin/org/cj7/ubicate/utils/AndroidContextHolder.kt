package org.cj7.ubicate.utils

// composeApp/src/androidMain/kotlin/org/cj7/ubicate/utils/AndroidContextHolder.kt


import android.content.Context

object AndroidContextHolder {
    lateinit var appContext: Context
        private set

    fun init(context: Context) {
        appContext = context.applicationContext
    }
}
