package org.cj7.ubicate.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.cj7.ubicate.components.CategoriesComponent
import org.cj7.ubicate.components.DisfrutaDeLoja
import org.cj7.ubicate.components.ImageCarousel
import org.cj7.ubicate.components.LoadingScreen
import org.cj7.ubicate.components.LocalesView
import org.cj7.ubicate.components.SearchBarComponent

@Composable
fun MainMenu(
    navigatetoLocalContent: (String) -> Unit,
    navigatetolocalGPS: (String) -> Unit
) {
    val searchQuery = remember { mutableStateOf("") }
    val isLoading = remember { mutableStateOf(false) }
    val progress = remember { mutableStateOf(0f) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.statusBars.asPaddingValues())
            .background(MaterialTheme.colorScheme.background)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 12.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    "EVENTOS Y NOTICIAS",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(10.dp))
                ImageCarousel()
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    "CONOCE LA CIUDAD",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(10.dp))
              //  DisfrutaDeLoja(navigatetolocalGPS)
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    "CATEGORÍAS",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                CategoriesComponent { searchQuery.value = it }
                Spacer(modifier = Modifier.height(10.dp))

                SearchBarComponent { searchQuery.value = it }
                Spacer(modifier = Modifier.height(10.dp))
            }

            item {
                LocalesView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 100.dp, max = 600.dp), // altura finita
                    searchQuery = searchQuery.value,
                    navigatetoLocalContent = navigatetoLocalContent,
                )
            }
        }



        // Pantalla de carga
        if (isLoading.value) {
            LoadingScreen(isLoading = isLoading.value, progress = progress.value)
        }
    }
}
