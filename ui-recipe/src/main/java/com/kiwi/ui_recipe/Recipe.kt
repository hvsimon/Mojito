package com.kiwi.ui_recipe

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.statusBarsPadding

data class Ingredient(
    val name: String,
    val amount: String,
)

@Preview
@Composable
fun Recipe() {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Mojito",
            style = MaterialTheme.typography.displaySmall,
        )
        Ingredient()
    }

}

@Composable
fun Ingredient() {
    val ingredients = listOf(
        Ingredient("White Rum", "2 shots"),
        Ingredient("Sugar Syrup", "0.5 shot"),
        Ingredient("Lime Wedges", "4"),
        Ingredient("Fresh Mint", "12 leaves"),
        Ingredient("Soda Water", "fill to top"),
    )

    Column {
        ingredients.forEach {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .height(48.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = it.name,
                    style = MaterialTheme.typography.headlineSmall,
                )
                Text(
                    text = it.amount,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }

            Divider()
        }
    }
}