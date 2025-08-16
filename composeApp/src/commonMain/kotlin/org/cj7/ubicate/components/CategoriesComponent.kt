package org.cj7.ubicate.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import ubicate.composeapp.generated.resources.Res
import ubicate.composeapp.generated.resources.all
import ubicate.composeapp.generated.resources.entertainment
import ubicate.composeapp.generated.resources.entertainment18
import ubicate.composeapp.generated.resources.restaurant
import ubicate.composeapp.generated.resources.technology
import ubicate.composeapp.generated.resources.market
import ubicate.composeapp.generated.resources.paper
import ubicate.composeapp.generated.resources.health
import ubicate.composeapp.generated.resources.cars
import ubicate.composeapp.generated.resources.sports
import ubicate.composeapp.generated.resources.clothes
import ubicate.composeapp.generated.resources.makeup
import ubicate.composeapp.generated.resources.pets

data class Category(
    val title: String,
    val image: DrawableResource // Aquí usamos DrawableResource, no Int
)

@Composable
fun CategoriesComponent(onSearch: (String) -> Unit) {
    val query = remember { mutableStateOf("") }

    val categories = remember {
        listOf(
            Category("Todos", Res.drawable.all),
            Category("Restaurante", Res.drawable.restaurant),
            Category("Tecnologia", Res.drawable.technology),
            Category("Entretenimiento", Res.drawable.entertainment),
            Category("Zona Adulta", Res.drawable.entertainment18),
            Category("Super/Tienda", Res.drawable.market),
            Category("Manualidades", Res.drawable.paper),
            Category("Salud", Res.drawable.health),
            Category("Carros", Res.drawable.cars),
            Category("Deportes", Res.drawable.sports),
            Category("Ropa/Moda", Res.drawable.clothes),
            Category("Estetica", Res.drawable.makeup),
            Category("Mascotas", Res.drawable.pets)
        )
    }

    CategoriesRow(categories = categories) { selectedCategory ->
        query.value = selectedCategory
        onSearch(selectedCategory)
    }
}

@Composable
fun CategoriesRow(
    categories: List<Category>,
    onCategoryClick: (String) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(36.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(categories) { category ->
            CategoryItem(
                title = category.title,
                image = category.image,
                onClick = { onCategoryClick(category.title) }
            )
        }
    }
}

@Composable
fun CategoryItem(
    title: String,
    image: DrawableResource, // DrawableResource
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(100.dp)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val shape = RoundedCornerShape(4.dp)

        Box(
            modifier = Modifier
                .size(70.dp)
                .clip(shape)
                .background(Color.Transparent),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(image), // painterResource acepta DrawableResource
                contentDescription = title,
                modifier = Modifier
                    .size(64.dp)
                    .clip(shape),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = title,
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.Center,
            fontSize = 13.sp
        )
    }
}
