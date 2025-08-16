package org.cj7.ubicate.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import ubicate.composeapp.generated.resources.Res
import ubicate.composeapp.generated.resources.logoubicatesinfondo


@Composable
fun SearchBarComponent(onSearch: (String) -> Unit) {
    val query = remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        TextField(
            value = query.value,
            onValueChange = {
                query.value = it
                onSearch(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color(172, 180, 251),
                    shape = RoundedCornerShape(5.dp)
                ),
            shape = RoundedCornerShape(20.dp),
            placeholder = {
                Text(
                    text = "¿Qué necesitas... ?",
                    color = Color.Black,
                    fontSize = 16.sp
                )
            },
            singleLine = true,
            leadingIcon = {
                Image(
                    painter = painterResource(Res.drawable.logoubicatesinfondo),
                    contentDescription = "Custom Search Icon",
                    modifier = Modifier
                        .size(60.dp)
                        .padding(start = 8.dp),
                    contentScale = ContentScale.Fit
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = Color.Black,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                disabledTextColor = Color.Gray
            )
        )
    }
}
