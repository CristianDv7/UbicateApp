// composeApp/src/commonMain/kotlin/org/cj7/ubicate/components/LocalSocialButtons.kt
package org.cj7.ubicate.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.cj7.ubicate.model.LocalesUbicate
import org.cj7.ubicate.model.ServiciosData
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.cj7.ubicate.utils.openUrl
import ubicate.composeapp.generated.resources.Res
import ubicate.composeapp.generated.resources.facebook
import ubicate.composeapp.generated.resources.instagram
import ubicate.composeapp.generated.resources.tiktok
import ubicate.composeapp.generated.resources.whatsapp


@Composable
fun LocalSocialButtons(local: LocalesUbicate) {
    Column(modifier = Modifier.fillMaxWidth()) {
        if (local.ahorita.isNotEmpty()) {
            Row(modifier = Modifier.fillMaxWidth()) {
                SocialButton(
                    label = "Pagar con Banco de Loja",
                    url = local.ahorita,
                    color = Color(172, 180, 251)
                )
            }
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.weight(1f)) {
                if (local.whatsapp.isNotEmpty()) {
                    SocialButtonWithIcon(
                        icon = Res.drawable.whatsapp,
                        label = "WhatsApp",
                        url = local.whatsapp,
                        color = Color(0x0F232121),
                        iconDesc = "Whatsapp Logo"
                    )
                }
                if (local.instagram.isNotEmpty()) {
                    SocialButtonWithIcon(
                        icon = Res.drawable.instagram,
                        label = "Instagram",
                        url = local.instagram,
                        color = Color(0x0F232121),
                        iconDesc = "Instagram Logo"
                    )
                }
            }
            Column(modifier = Modifier.weight(1f)) {
                if (local.facebook.isNotEmpty()) {
                    SocialButtonWithIcon(
                        icon = Res.drawable.facebook,
                        label = "Facebook",
                        url = local.facebook,
                        color = Color(0x0F232121),
                        iconDesc = "Facebook Logo"
                    )
                }
                if (local.tiktok.isNotEmpty()) {
                    SocialButtonWithIcon(
                        icon = Res.drawable.tiktok,
                        label = "Tiktok",
                        url = local.tiktok,
                        color = Color(0x0F232121),
                        iconDesc = "Tiktok Logo"
                    )
                }
            }
        }
    }
}

@Composable
fun ServiciosSocialButtons(local: ServiciosData) {
    Column(modifier = Modifier.fillMaxWidth()) {
        if (local.whatsapp.isNotEmpty()) {
            SocialButtonWithIcon(
                icon = Res.drawable.whatsapp,
                label = "WhatsApp",
                url = local.whatsapp,
                color = Color(0x0F232121),
                iconDesc = "Whatsapp Logo"
            )
        }
        if (local.facebook.isNotEmpty()) {
            SocialButtonWithIcon(
                icon = Res.drawable.facebook,
                label = "Facebook",
                url = local.facebook,
                color = Color(0x0F232121),
                iconDesc = "Facebook Logo"
            )
        }
        if (local.instagram.isNotEmpty()) {
            SocialButtonWithIcon(
                icon = Res.drawable.instagram,
                label = "Instagram",
                url = local.instagram,
                color = Color(0x0F232121),
                iconDesc = "Instagram Logo"
            )
        }
        if (local.tiktok.isNotEmpty()) {
            SocialButtonWithIcon(
                icon = Res.drawable.tiktok,
                label = "Tiktok",
                url = local.tiktok,
                color = Color(0x0F232121),
                iconDesc = "Tiktok Logo"
            )
        }
    }
}

@Composable
fun SocialButton(
    label: String,
    url: String,
    color: Color
) {
    Button(
        onClick = { openUrl(url) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color)
    ) {
        Text(text = label, color = Color.Black)
    }
}

@Composable
fun SocialButtonWithIcon(
    label: String,
    url: String,
    color: Color,
    icon: DrawableResource,
    iconDesc: String
) {
    Button(
        onClick = { openUrl(url) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(icon),
                contentDescription = iconDesc,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .size(24.dp)
            )
            Text(text = label, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}
