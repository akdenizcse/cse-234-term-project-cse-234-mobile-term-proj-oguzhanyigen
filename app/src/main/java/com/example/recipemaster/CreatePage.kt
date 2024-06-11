package com.example.recipemaster

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePage(onCreateClick: () -> Unit = { /* TODO: Implement create recipe action */ },
               onImageUploadClick: () -> Unit = { /* TODO: Implement image upload action */ }) {
    var title by remember { mutableStateOf("") }
    var ingredients by remember { mutableStateOf("") }
    var instructions by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(Color.Transparent),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Recipe Title", style = TextStyle(color = Color.DarkGray)) },
                placeholder = { Text("Enter Title", style = TextStyle(color = Color.DarkGray)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 1.dp, color = Color.DarkGray, shape = RoundedCornerShape(8.dp)),
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = ingredients,
                onValueChange = { ingredients = it },
                label = { Text("Ingredients", style = TextStyle(color = Color.DarkGray)) },
                placeholder = { Text("Enter Ingredients", style = TextStyle(color = Color.DarkGray)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 1.dp, color = Color.DarkGray, shape = RoundedCornerShape(8.dp)),
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = instructions,
                onValueChange = { instructions = it },
                label = { Text("Enter Instructions", style = TextStyle(color = Color.DarkGray)) },
                placeholder = { Text("Enter Instructions", style = TextStyle(color = Color.DarkGray)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 1.dp, color = Color.DarkGray, shape = RoundedCornerShape(8.dp)),
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 1.dp, color = Color.DarkGray, shape = RoundedCornerShape(8.dp))
                    .height(56.dp)
                    .clickable(onClick = onImageUploadClick),
                contentAlignment = Alignment.Center
            ) {
                Text("Upload Image (Optional)", color = Color.DarkGray)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    onCreateClick() // Trigger the create recipe action
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(Color(0xff129575))
            ) {
                Text("Create Recipe", color = Color.White)
            }
        }
    }
}
