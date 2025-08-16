// composeApp/src/androidMain/kotlin/org/cj7/ubicate/utils/UrlOpener.android.kt
package org.cj7.ubicate.utils

import android.content.Intent
import android.net.Uri

actual fun openUrl(url: String) {
    // Requiere que hayas llamado AndroidContextHolder.init(...) en tu MainActivity
    val ctx = AndroidContextHolder.appContext
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    ctx.startActivity(intent)
}
