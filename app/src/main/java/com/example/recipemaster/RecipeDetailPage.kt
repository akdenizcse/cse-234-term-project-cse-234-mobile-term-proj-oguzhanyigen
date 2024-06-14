package com.example.recipemaster

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
            .padding(16.dp)
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
                Text(
                    text = it.title,
                    style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Ingredients: ${it.ingredients}",
                    style = TextStyle(fontSize = 18.sp, color = Color.Gray)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Instructions: ${it.instructions}",
                    style = TextStyle(fontSize = 18.sp, color = Color.Gray)
                )
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
