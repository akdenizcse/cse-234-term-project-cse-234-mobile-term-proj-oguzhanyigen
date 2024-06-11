package com.example.recipemaster

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
                time = recipe.time
            )
        }
    }
    }

}

@Composable
fun ProfileHeader() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
       /* Image(
            painter = rememberAsyncImagePainter("https://example.com/avatar.jpg"), // Replace with actual image URL
            contentDescription = "Profile Image",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )*/
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
        TextButton(onClick = { /* TODO: Add click action */ }) {
            Text(
                text = "More...",
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}


@Composable
fun RecipeCard(title: String, author: String, time: String) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            /* Uncomment and replace with actual image URL if needed
            Image(
                painter = rememberAsyncImagePainter("https://example.com/recipe1.jpg"), // Replace with actual image URL
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            )
            */
            Spacer(modifier = Modifier.height(8.dp))
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "By $author",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Text(
                        text = time,
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.weight(1f))

                    Text(text = "IconHere") // Replace with actual icon if needed
                }
            }
        }
    }
}


val recipes =  listOf(
        Recipe(
            title = "Traditional spare ribs baked",
            author = "Chef John",
            time = "20 min"
        ),
        Recipe(
            title = "Spice roasted chicken with flavored rice",
            author = "Mark Kelvin",
            time = "20 min"
        ),
    Recipe(
        title = "Spice roasted chicken with flavored rice",
        author = "Mark Kelvin",
        time = "20 min"
    ),
    Recipe(
        title = "Spice roasted chicken with flavored rice",
        author = "Mark Kelvin",
        time = "20 min"
    ),
    Recipe(
        title = "Spice roasted chicken with flavored rice",
        author = "Mark Kelvin",
        time = "20 min"
    ),
    Recipe(
        title = "Spice roasted chicken with flavored rice",
        author = "Mark Kelvin",
        time = "20 min"
    ),
    Recipe(
        title = "Spice roasted chicken with flavored rice",
        author = "Mark Kelvin",
        time = "20 min"
    ),
        // Add more recipes as needed
    )

data class Recipe(
    val title: String,
    val author: String,
    val time: String
)
