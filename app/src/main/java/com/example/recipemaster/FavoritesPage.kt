package com.example.recipemaster

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun FavoritesPage(navController: NavController) {
    var recipes by remember { mutableStateOf<List<HomeRecipe>?>(null) }
    var favoriteRecipeIds by remember { mutableStateOf<List<Int>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        fetchRecipesFavPage(context) { result, error ->
            if (result != null) {
                recipes = result
            } else {
                errorMessage = error
            }
        }

        fetchFavoriteRecipesFavPage(context) { result, error ->
            if (result != null) {
                favoriteRecipeIds = result
                loading = false
            } else {
                errorMessage = error
            }
        }
    }

    val favoriteRecipes = recipes?.filter { favoriteRecipeIds.contains(it.id) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Saved Recipes",
            fontSize = 22.sp,
            color = Color.Black,
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.SemiBold
        )

        if (loading) {
            CircularProgressIndicator()
        } else if (errorMessage != null) {
            Text(
                text = errorMessage ?: "Unknown error",
                color = Color.Red
            )
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.background(Color.White)
            ) {
                favoriteRecipes?.let {
                    items(it) { recipe ->
                        FavoritesRecipeCard(
                            title = recipe.title,
                            ingredients = recipe.ingredients, // Replace with actual author if available
                            instructions = recipe.instructions, // Replace with actual cooking time if available
                            image = recipe.image,
                            onCardClick = {
                                navController.navigate("recipeDetail/${recipe.id}")
                            }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun FavoritesRecipeCard(
    title: String,
    ingredients: String,
    instructions: String,
    image: String?,
    onCardClick: () -> Unit
) {
    val imageUrl = "http://192.168.0.157:8000/images/$image" // Adjust the base URL as necessary

    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onCardClick() }
    ) {
        Box {
            Image(
                painter = rememberImagePainter(data = imageUrl),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(175.dp)
                    .fillMaxWidth()
                    .alpha(0.8f) // Adjust the alpha value for desired transparency
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Spacer(modifier = Modifier.height(85.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = ingredients,
                        fontSize = 14.sp,
                        color = Color.White
                    )
                    Text(
                        text = instructions,
                        fontSize = 14.sp,
                        color = Color.White,
                        maxLines = 2,
                    )
                }
            }
            Icon(
                imageVector = Icons.Outlined.Favorite,
                tint = Color.Red,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
            )
        }
    }

}

fun fetchRecipesFavPage(context: Context, onResult: (List<HomeRecipe>?, String?) -> Unit) {
    val apiService = ApiConnections.RetrofitClient.getInstance(context)
    val call = apiService.getRecipes()
    call.enqueue(object : Callback<List<HomeRecipe>> {
        override fun onResponse(call: Call<List<HomeRecipe>>, response: Response<List<HomeRecipe>>) {
            if (response.isSuccessful) {
                onResult(response.body(), null)
            } else {
                onResult(null, response.message())
            }
        }

        override fun onFailure(call: Call<List<HomeRecipe>>, t: Throwable) {
            onResult(null, t.message)
        }
    })
}

fun fetchFavoriteRecipesFavPage(context: Context, onResult: (List<Int>?, String?) -> Unit) {
    val apiService = ApiConnections.RetrofitClient.getInstance(context)
    val authToken = runBlocking { UserPreferences.getUserTokenSync(context) }
    val call = apiService.getFavorites("Bearer $authToken")
    call.enqueue(object : Callback<List<Int>> {
        override fun onResponse(call: Call<List<Int>>, response: Response<List<Int>>) {
            if (response.isSuccessful) {
                onResult(response.body(), null)
            } else {
                onResult(null, response.message())
            }
        }

        override fun onFailure(call: Call<List<Int>>, t: Throwable) {
            onResult(null, t.message)
        }
    })
}
