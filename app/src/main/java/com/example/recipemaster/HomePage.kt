package com.example.recipemaster

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomePage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Hello John",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "What are you cooking today?",
                    style = TextStyle(fontSize = 18.sp, color = Color.Gray)
                )
            }
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(Color.Gray, shape = RoundedCornerShape(50))
                    .clickable { /* Handle profile click */ }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.chef2), // Replace with your image resource
                    contentDescription = null,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(
                listOf(
                    "All",
                    "Indian",
                    "Italian",
                    "Asian",
                    "Chinese",
                    "Mexican",
                    "French"
                )
            ) { filter ->
                FilterButton(filter)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Recommended Recipes",
            style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp)
        ) {
            itemsIndexed(homerecipes.chunked(2)) { index, pair ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    pair.forEach { homerecipe ->
                        HomeRecipeCard(
                            recipe = homerecipe,
                            onCardClick = {
                                // Navigate to the specific recipe
                                Log.d("RecipeCard", "Clicked on ${homerecipe.name}")
                            },
                            onFavoriteClick = { id, isFavorite ->
                                if (isFavorite) {
                                    favoriteRecipes.add(id)
                                } else {
                                    favoriteRecipes.remove(id)
                                }
                                Log.d("FavoriteIcon", "Favorite clicked on recipe ID $id, isFavorite: $isFavorite, favoriteRecipes: $favoriteRecipes")
                            }
                        )
                    }
                }
            }
        }
    }
}

val favoriteRecipes = mutableListOf<Int>()

val homerecipes = listOf(
    HomeRecipe(
        id = 1,
        name = "Chicken",
        time = "20 min",
        rating = 4f
    ),
    HomeRecipe(
        id = 2,
        name = "Chicken",
        time = "20 min",
        rating = 4f
    ),
    HomeRecipe(
        id = 3,
        name = "Chicken",
        time = "20 min",
        rating = 4f
    ),
    HomeRecipe(
        id = 4,
        name = "Chicken",
        time = "20 min",
        rating = 4f
    ),
    HomeRecipe(
        id = 5,
        name = "Chicken",
        time = "20 min",
        rating = 4f
    ),
    HomeRecipe(
        id = 6,
        name = "Chicken",
        time = "20 min",
        rating = 4f
    ),
    HomeRecipe(
        id = 7,
        name = "Chicken",
        time = "20 min",
        rating = 4f
    ),
)

@Composable
fun FilterButton(text: String) {
    Button(
        onClick = { /* Handle filter click */ },
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .background(Color.White, shape = RoundedCornerShape(20.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(text = text, color = Color.Black)
    }
}
data class HomeRecipe(val id: Int, val name: String, val time: String, val rating: Float)
@Composable
fun HomeRecipeCard(
    recipe: HomeRecipe,
    onCardClick: () -> Unit,
    onFavoriteClick: (Int, Boolean) -> Unit
) {
    var isFavorite by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .size(160.dp)
            .padding(8.dp)
            .clickable { onCardClick() },
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.food1), // Replace with your image resource
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .align(Alignment.Center)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                )

                IconButton(
                    onClick = {
                        isFavorite = !isFavorite
                        onFavoriteClick(recipe.id, isFavorite)
                    },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = (16).dp, y = (-8).dp) // Adjust offset to move it more to the top right
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Outlined.Favorite else Icons.Outlined.FavoriteBorder, // Toggle icon
                        contentDescription = null,
                        tint = Color.Red,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = recipe.name,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                ),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Time: ${recipe.time}",
                style = TextStyle(fontSize = 12.sp, color = Color.Gray),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = recipe.rating.toString(),
                    style = TextStyle(fontSize = 12.sp, color = Color.Gray),
                    textAlign = TextAlign.Center
                )
                Icon(
                    imageVector = Icons.Filled.Star, // Replace with your star icon resource
                    contentDescription = null,
                    tint = Color.Yellow,
                    modifier = Modifier.size(12.dp)
                )
            }
        }
    }
}