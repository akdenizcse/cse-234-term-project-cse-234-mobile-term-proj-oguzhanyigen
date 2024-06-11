package com.example.recipemaster

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchPage() {
    var searchQuery by remember { mutableStateOf("") }
    val filteredRecipes = searchRecipes.filter {
        it.title.contains(searchQuery, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(horizontal = 4.dp).border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(8.dp)),
            label = { Text(text = "Search Recipes") },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = Color.Black,
            )
        )

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.background(Color.White)
        ) {
            items(filteredRecipes) { searchrecipe ->
                SearchRecipeCard(
                    title = searchrecipe.title,
                    author = searchrecipe.author,
                    time = searchrecipe.time,
                    imageResId = searchrecipe.imageUrl
                )
            }
        }
    }
}

@Composable
fun SearchRecipeCard(title: String, author: String, time: String, imageResId: Int) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Box {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(175.dp)
                    .fillMaxWidth()
                    .alpha(0.55f) // Adjust the alpha value for desired transparency
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Spacer(modifier = Modifier.height(85.dp))
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "By $author",
                        fontSize = 14.sp,
                        color = Color.White
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = time,
                            fontSize = 14.sp,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = Icons.Outlined.FavoriteBorder,
                            tint = Color.Red,
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}


val searchRecipes = listOf(
    SearchRecipe(
        title = "Traditional spare ribs baked",
        author = "Chef John",
        time = "20 min",
        imageUrl = R.drawable.food1
    ),
    SearchRecipe(
        title = "Spice roasted chicken with flavored rice",
        author = "Mark Kelvin",
        time = "20 min",
        imageUrl = R.drawable.food1
    ),
    SearchRecipe(
        title = "Spice roasted chicken with flavored rice",
        author = "Mark Kelvin",
        time = "20 min",
        imageUrl = R.drawable.food1
    ),
    SearchRecipe(
        title = "Spice roasted chicken with flavored rice",
        author = "Mark Kelvin",
        time = "20 min",
        imageUrl = R.drawable.food1
    ),
    SearchRecipe(
        title = "Spice roasted chicken with flavored rice",
        author = "Mark Kelvin",
        time = "20 min",
        imageUrl = R.drawable.food1
    ),
    SearchRecipe(
        title = "Spice roasted chicken with flavored rice",
        author = "Mark Kelvin",
        time = "20 min",
        imageUrl = R.drawable.food1
    ),
    SearchRecipe(
        title = "Spice roasted chicken with flavored rice",
        author = "Mark Kelvin",
        time = "20 min",
        imageUrl = R.drawable.food1
    ),
    // Add more recipes as needed
)

data class SearchRecipe(
    val title: String,
    val author: String,
    val time: String,
    val imageUrl: Int
)
