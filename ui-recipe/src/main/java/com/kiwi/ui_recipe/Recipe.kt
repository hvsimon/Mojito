package com.kiwi.ui_recipe

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import com.kiwi.common_ui_compose.rememberFlowWithLifecycle
import com.kiwi.data.entities.Cocktail
import com.kiwi.data.entities.Ingredient
import java.util.UUID

@Composable
fun Recipe(
    viewModel: RecipeViewModel = hiltViewModel(),
) {
    val cocktail: Cocktail? by rememberFlowWithLifecycle(viewModel.cocktail).collectAsState(initial = null)

    Recipe(cocktail)
}

@Composable
private fun Recipe(
    cocktail: Cocktail?,
) {
    if (cocktail == null) {
        // show loading view
        return
    }

    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
    ) {
        Image(
            painter = rememberImagePainter(
                data = cocktail.gallery.firstOrNull(),
                builder = {
                    size(OriginalSize)
                },
            ),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        )
        Column {
            Introduction(
                cocktailName = cocktail.name,
                cocktailIntro = cocktail.intro
            )
            Ingredient(cocktail.ingredients)
            Step(cocktail.steps)
            Tips(cocktail.tips)
        }
    }
}

@Composable
private fun Introduction(
    cocktailName: String,
    cocktailIntro: String,
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = cocktailName,
            style = MaterialTheme.typography.displaySmall,
        )

        Text(
            text = cocktailIntro,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Composable
private fun Ingredient(ingredients: List<Ingredient>) {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.surfaceVariant)
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.ingredients),
            style = MaterialTheme.typography.headlineLarge,
        )
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
            Divider(
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun Step(
    steps: List<String>,
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = stringResource(id = R.string.step),
            style = MaterialTheme.typography.headlineLarge,
        )

        Text(
            text = steps.joinToString(separator = "\n"),
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Composable
private fun Tips(
    tips: Set<String>,
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.tips),
            style = MaterialTheme.typography.headlineLarge,
        )

        Text(
            text = tips.joinToString(separator = "\n"),
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Preview
@Composable
fun PreviewRecipe() {
    Recipe(
        cocktail = Cocktail(
            cocktailId = UUID.randomUUID().toString(),
            name = "Mojito2",
            gallery = listOf("https://images.unsplash.com/photo-1609345265499-2133bbeb6ce5?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1994&q=80"),
            intro = "The refreshingly minty-citrus flavours of Cuba's Mojito cocktail perfectly compliment white Rum & is wonderful on a hot summer's day. Highly popular, yet tricky to get right & often served too sweet - the key to a good Mojito is to use plenty of fresh Mint & Lime juice but not too much Sugar.",
            ingredients = listOf(
                Ingredient(
                    name = "\uD83C\uDF78 白蘭姆酒",
                    amount = "2 shots"
                ),
                Ingredient(
                    name = "\uD83E\uDDC2 糖漿",
                    amount = "0.5 shot"
                ),
                Ingredient(
                    name = "\uD83C\uDF4B 萊姆片",
                    amount = "4"
                ),
                Ingredient(
                    name = "\uD83C\uDF3F 新鮮薄荷",
                    amount = "12 leaves"
                ),
                Ingredient(
                    name = "\uD83E\uDD64 蘇打水",
                    amount = "fill to top"
                ),
            ),
            steps = listOf(
                "1. Place the Mint, Sugar Syrup & Lime wedges into a highball glass & lightly muddle the ingredients together. The Lime wedges & Mint leaves should be bruised to release their juices & essential oils.",
                "2. Fill the glass with crushed ice, pour over the White Rum & stir.",
                "3. Top up with Soda Water & stir well from the bottom up.",
                "4. Garnish with a sprig of Mint & serve with a straw.",
            ),
            tips = setOf(
                "Given that the Mojito is reliant on fresh Limes & Mint that are available in different sizes & flavour intensity, it is a cocktail that really rewards you if you tune the quantities to accommodate the ingredients you have available & most importantly, your own personal tastes.",
                "If you make a Mojito & find it too sweet, you could try adding Angostura bitters to cut down on the sweetness.",
                "Don't have any Limes to hand? Try using Lemons for a twist on the classic Mojito but note that you might need to add a little more Sugar Syrup or use a little less Lemon to balance out the extra sourness.",
                "If you're out of Sugar syrup, you can use half a teaspoon of fine caster Sugar instead. If using Sugar, you might like to add the White Rum & stir to dissolve the Sugar before adding the crushed Ice.",
                "Don't have crushed ice? Originally, Cuban bartenders mixed the drink using cubed ice, including mint stalks as well as leaves - try this for an equally tasty yet more rustic feel.",
            )
        )
    )
}
