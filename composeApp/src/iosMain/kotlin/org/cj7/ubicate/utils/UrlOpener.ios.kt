// composeApp/src/iosMain/kotlin/org/cj7/ubicate/utils/UrlOpener.ios.kt
package org.cj7.ubicate.utils

import platform.Foundation.NSURL
import platform.UIKit.UIApplication

actual fun openUrl(url: String) {
    val nsUrl = NSURL.URLWithString(url) ?: return
    UIApplication.sharedApplication.openURL(nsUrl)
}
