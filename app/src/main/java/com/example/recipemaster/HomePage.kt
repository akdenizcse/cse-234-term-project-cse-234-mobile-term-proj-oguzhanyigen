package com.example.recipemaster

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomePage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(text = "Hello John", style = TextStyle(fontSize = 24.sp, color = Color.Black))

        Spacer(modifier = Modifier.height(4.dp))

        Text(text = "What are you cooking today?", style = TextStyle(fontSize = 18.sp, color = Color.Gray))

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            FilterButton("All")
            FilterButton("Indian")
            FilterButton("Italian")
            FilterButton("Asian")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Recommended Recipes", style = TextStyle(fontSize = 18.sp, color = Color.Black))

        Spacer(modifier = Modifier.height(16.dp))

        RecipeCard("Classic Greek Salad", "15 Mins", 4.5f)
        Spacer(modifier = Modifier.height(16.dp))
        RecipeCard("Crunchy Nut Coleslaw", "10 Mins", 3.5f)
        Spacer(modifier = Modifier.height(16.dp))
        RecipeCard("Classic Greek Salad", "15 Mins", 4.5f)
        Spacer(modifier = Modifier.height(16.dp))
        RecipeCard("Crunchy Nut Coleslaw", "10 Mins", 3.5f)
    }
}

@Composable
fun FilterButton(text: String) {
    Button(onClick = { /* Handle filter click */ }) {
        Text(text)
    }
}

@Composable
fun RecipeCard(name: String, time: String, rating: Float) {
    Column(
        modifier = Modifier
            .background(Color.LightGray)
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(text = name, style = TextStyle(fontSize = 16.sp, color = Color.Black))
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "Time: $time", style = TextStyle(fontSize = 14.sp, color = Color.Gray))
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "Rating: $rating", style = TextStyle(fontSize = 14.sp, color = Color.Gray))
    }
}
