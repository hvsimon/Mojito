package com.kiwi.ui_collection

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.statusBarsPadding
import com.kiwi.common_ui_compose.KiwisBarTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Collection(
    openRecipe: () -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
    ) {
        stickyHeader {
            SectionHeader("Unforgettable Cocktails")
        }
        items(5) {
            CollectionItem(
                onItemClick = openRecipe
            )
        }
        stickyHeader {
            SectionHeader("Contemporary Classic Cocktails")
        }
        items(3) {
            CollectionItem(
                onItemClick = openRecipe
            )
        }
        stickyHeader {
            SectionHeader("New Era Cocktails")
        }
        items(10) {
            CollectionItem(
                onItemClick = openRecipe
            )
        }
    }
}

@Composable
fun SectionHeader(sectionName: String) {
    Text(
        text = sectionName,
        Modifier
            .background(color = MaterialTheme.colorScheme.surfaceVariant)
            .padding(16.dp)
            .fillMaxSize()
    )
}

@Composable
private fun CollectionItem(
    onItemClick: () -> Unit,
) {

    val cocktail = rememberSaveable { cocktails.random() }
    val ingredients = rememberSaveable {
        ingredients.shuffled().take((1..ingredients.size).random())
            .joinToString(prefix = "(", postfix = ")")
    }
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .clickable(onClick = onItemClick)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = cocktail,
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = ingredients
        )
    }
}

@Preview
@Composable
private fun OnboardingPreview() {
    KiwisBarTheme {
        Surface {
            Collection({})
        }
    }
}

private val cocktails = listOf(
    "Alexander",
    "Daiquiri",
    "Negroni",
    "Screwdriver",
    "Americano",
    "Derby",
    "Old Fashioned",
)

private val ingredients = listOf(
    "Gin",
    "Campari",
    "Red Vermouth",
    "Bourbon Whiskey",
    "Simple Syrup",
    "Angostura Bitters",
    "Cointreau",
    "Lemon Juice",
)