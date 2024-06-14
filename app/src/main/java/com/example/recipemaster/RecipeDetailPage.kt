package com.example.recipemaster

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

@OptIn(ExperimentalCoilApi::class)
@Composable
fun RecipeDetailPage(recipeId: Int) {
    var recipe by remember { mutableStateOf<HomeRecipe?>(null) }
    var loading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    LaunchedEffect(recipeId) {
        fetchRecipeById(context, recipeId) { result, error ->
            loading = false
            if (result != null) {
                recipe = result
            } else {
                errorMessage = error
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (loading) {
            CircularProgressIndicator()
        } else if (errorMessage != null) {
            Text(
                text = errorMessage ?: "Unknown error",
                style = TextStyle(fontSize = 16.sp, color = Color.Red)
            )
        } else {
            recipe?.let {
                val imageUrl = "http://192.168.0.157:8000/images/${it.image}"

                Image(
                    painter = rememberImagePainter(data = imageUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(15.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = it.title,
                    style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(
                    text = "Ingredients:",
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = Color.Black)
                )
                Text(
                    text = it.ingredients,
                    style = TextStyle(fontSize = 18.sp, color = Color.Gray),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(
                    text = "Instructions:",
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = Color.Black)
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    item {
                        Text(
                            text = it.instructions,
                            style = TextStyle(fontSize = 17.sp, color = Color.Gray),
                            //maxLines = 10, // you can adjust the max lines as needed
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }
                }
            }
        }
    }
}

fun fetchRecipeById(context: Context, recipeId: Int, onResult: (HomeRecipe?, String?) -> Unit) {
    val apiService = ApiConnections.RetrofitClient.getInstance(context)
    val call = apiService.getRecipeById(recipeId)
    call.enqueue(object : Callback<HomeRecipe> {
        override fun onResponse(call: Call<HomeRecipe>, response: Response<HomeRecipe>) {
            if (response.isSuccessful) {
                onResult(response.body(), null)
            } else {
                onResult(null, response.message())
            }
        }

        override fun onFailure(call: Call<HomeRecipe>, t: Throwable) {
            onResult(null, t.message)
        }
    })
}
