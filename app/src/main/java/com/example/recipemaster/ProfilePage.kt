package com.example.recipemaster

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi

@Composable
fun ProfilePage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ){

    ProfileHeader()

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.background(Color.White)
        ) {
            items(recipes) { recipe ->
                RecipeCard(
                    title = recipe.title,
                    author = recipe.author,
                    time = recipe.time,
                    imageResId = recipe.imageUrl // Update this line
                )
            }
        }
    }

}



@OptIn(ExperimentalCoilApi::class)
@Composable
fun ProfileHeader() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(R.drawable.chef2), // Replace with actual image URL
            contentDescription = "Profile Image",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Text(
            text = "Oğuzhan Yiğen",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 8.dp),
            color = Color.Black
        )
        Text(
            text = "Chef",
            fontSize = 16.sp,
            color = Color.Gray
        )
        Text(
            text = "Private Chef\nPassionate about food and life 🍲🍔🍕",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 8.dp)
        )
        /*TextButton(onClick = { *//* }) {
            Text(
                text = "More...",
                color = MaterialTheme.colorScheme.primary
            )
        }*/
    }
}


@Composable
fun RecipeCard(title: String, author: String, time: String, imageResId: Int) {
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
                        Text(text = "IconHere", color = Color.White) // Replace with actual icon if needed
                    }
                }
            }
        }
    }
}


val recipes =  listOf(
        Recipe(
            title = "Traditional spare ribs baked",
            author = "Chef John",
            time = "20 min",
            imageUrl = R.drawable.food1
        ),
        Recipe(
            title = "Spice roasted chicken with flavored rice",
            author = "Mark Kelvin",
            time = "20 min",
            imageUrl = R.drawable.food1
        ),
    Recipe(
        title = "Spice roasted chicken with flavored rice",
        author = "Mark Kelvin",
        time = "20 min",
        imageUrl = R.drawable.food1
    ),
    Recipe(
        title = "Spice roasted chicken with flavored rice",
        author = "Mark Kelvin",
        time = "20 min",
        imageUrl = R.drawable.food1
    ),
    Recipe(
        title = "Spice roasted chicken with flavored rice",
        author = "Mark Kelvin",
        time = "20 min",
        imageUrl = R.drawable.food1
    ),
    Recipe(
        title = "Spice roasted chicken with flavored rice",
        author = "Mark Kelvin",
        time = "20 min",
        imageUrl = R.drawable.food1
    ),
    Recipe(
        title = "Spice roasted chicken with flavored rice",
        author = "Mark Kelvin",
        time = "20 min",
        imageUrl = R.drawable.food1
    ),
        // Add more recipes as needed
    )

data class Recipe(
    val title: String,
    val author: String,
    val time: String,
    val imageUrl: Int
)