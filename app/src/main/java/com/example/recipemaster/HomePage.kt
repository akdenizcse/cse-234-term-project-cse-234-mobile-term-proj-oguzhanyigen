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
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(navController: NavController) {
    var recipes by remember { mutableStateOf<List<HomeRecipe>?>(null) }
    var favoriteRecipeIds by remember { mutableStateOf<List<Int>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var searchQuery by remember { mutableStateOf("") }
    var userName by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current
    val userId = runBlocking { UserPreferences.getUserId(context).first() } ?: 0

    LaunchedEffect(Unit) {
        fetchRecipes(context) { result, error ->
            loading = false
            if (result != null) {
                recipes = result
            } else {
                errorMessage = error
            }
        }

        fetchUser(context, userId) { user, error ->
            if (user != null) {
                userName = user.name
            } else {
                errorMessage = error
            }
        }

        fetchFavoriteRecipes(context) { result, error ->
            if (result != null) {
                favoriteRecipeIds = result
            } else {
                errorMessage = error
            }
        }
    }

    val filteredRecipes = recipes?.filter { it.title.contains(searchQuery, ignoreCase = true) }

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
                    text = "Hello ${userName ?: "User"}",
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
                    painter = painterResource(id = R.drawable.chef2),
                    contentDescription = null,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search Recipes") },
            placeholder = { Text("Enter recipe title") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(8.dp)),
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text
            ),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (loading) {
            CircularProgressIndicator()
        } else if (errorMessage != null) {
            Text(
                text = errorMessage ?: "Unknown error",
                style = TextStyle(fontSize = 16.sp, color = Color.Red)
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp)
            ) {
                filteredRecipes?.let {
                    items(it) { recipe ->
                        HomeRecipeCard(
                            recipe = recipe,
                            isFavorite = favoriteRecipeIds.contains(recipe.id),
                            onCardClick = {
                                navController.navigate("recipeDetail/${recipe.id}")
                            },
                            onFavoriteClick = { id, isFavorite ->
                                handleFavoriteClick(context, id, isFavorite)
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
fun HomeRecipeCard(
    recipe: HomeRecipe,
    isFavorite: Boolean,
    onCardClick: () -> Unit,
    onFavoriteClick: (Int, Boolean) -> Unit
) {
    var favoriteState by remember { mutableStateOf(isFavorite) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
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
                    .height(160.dp)
            ) {
                val imageUrl = "http://192.168.0.157:8000/images/${recipe.image}" // Adjust the base URL as necessary
                Image(
                    painter = rememberImagePainter(data = imageUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(15.dp)),
                    contentScale = ContentScale.Crop
                )

                IconButton(
                    onClick = {
                        favoriteState = !favoriteState
                        onFavoriteClick(recipe.id, favoriteState)
                    },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                ) {
                    Icon(
                        imageVector = if (favoriteState) Icons.Outlined.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = null,
                        tint = Color.Red,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = recipe.title,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Ingredients: ${recipe.ingredients}",
                style = TextStyle(fontSize = 14.sp, color = Color.Gray,),
                textAlign = TextAlign.Center,

            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = recipe.instructions,
                    style = TextStyle(fontSize = 14.sp, color = Color.Gray),
                    textAlign = TextAlign.Center,
                    maxLines = 3,
                )
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    tint = Color.Yellow,
                    modifier = Modifier.size(0.dp)
                )
            }
        }
    }
}


fun fetchRecipes(context: Context, onResult: (List<HomeRecipe>?, String?) -> Unit) {
    val apiService = ApiConnections.RetrofitClient.getInstance(context)
    val call = apiService.getRecipes()
    call.enqueue(object : Callback<List<HomeRecipe>> {
        override fun onResponse(
            call: Call<List<HomeRecipe>>,
            response: Response<List<HomeRecipe>>
        ) {
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

fun fetchUser(context: Context, userId: Int, onResult: (User?, String?) -> Unit) {
    val apiService = ApiConnections.RetrofitClient.getInstance(context)
    val authToken = runBlocking { UserPreferences.getUserTokenSync(context) }
    val call = apiService.getUser("Bearer $authToken", userId)
    call.enqueue(object : Callback<User> {
        override fun onResponse(call: Call<User>, response: Response<User>) {
            if (response.isSuccessful) {
                onResult(response.body(), null)
            } else {
                onResult(null, response.message())
            }
        }

        override fun onFailure(call: Call<User>, t: Throwable) {
            onResult(null, t.message)
        }
    })
}

fun handleFavoriteClick(context: Context, recipeId: Int, isFavorite: Boolean) {
    val apiService = ApiConnections.RetrofitClient.getInstance(context)
    val authToken = runBlocking { UserPreferences.getUserTokenSync(context) }

    if (isFavorite) {
        val call = apiService.addFavorite("Bearer $authToken", recipeId)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (!response.isSuccessful) {
                    Log.e("Favorite", "Failed to add favorite: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("Favorite", "Failed to add favorite: ${t.message}")
            }
        })
    } else {
        val call = apiService.removeFavorite("Bearer $authToken", recipeId)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (!response.isSuccessful) {
                    Log.e("Favorite", "Failed to remove favorite: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("Favorite", "Failed to remove favorite: ${t.message}")
            }
        })
    }
}

fun fetchFavoriteRecipes(context: Context, onResult: (List<Int>?, String?) -> Unit) {
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
