package com.example.recipemaster

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun ProfilePage(navController: NavController) {
    var user by remember { mutableStateOf<User?>(null) }
    var recipes by remember { mutableStateOf<List<HomeRecipe>?>(null) }
    var loading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current
    val userId = runBlocking { UserPreferences.getUserId(context).first() } ?: 0

    LaunchedEffect(Unit) {
        fetchUserProfile(context, userId) { fetchedUser, error ->
            if (fetchedUser != null) {
                user = fetchedUser
            } else {
                errorMessage = error
            }
        }

        fetchUserRecipes(context, userId) { fetchedRecipes, error ->
            loading = false
            if (fetchedRecipes != null) {
                recipes = fetchedRecipes
            } else {
                errorMessage = error
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        ProfileHeader(user = user)

        if (loading) {
            CircularProgressIndicator()
        } else if (errorMessage != null) {
            Text(
                text = errorMessage ?: "Unknown error",
                color = Color.Red,
                style = TextStyle(fontSize = 16.sp)
            )
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.background(Color.White)
            ) {
                recipes?.let {
                    items(it) { recipe ->
                        HomeRecipeCard(
                            recipe = recipe,
                            isFavorite = false,
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

@Composable
fun ProfileHeader(user: User?) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(R.drawable.chef2), // Replace with actual image URL if available
            contentDescription = "Profile Image",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        user?.let {
            Text(
                text = it.name,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp),
                color = Color.Black
            )
            Text(
                text = it.job ?: "No job specified",
                fontSize = 16.sp,
                color = Color.Gray
            )
            Text(
                text = it.profiletext ?: "No profile text specified",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

fun fetchUserProfile(context: Context, userId: Int, onResult: (User?, String?) -> Unit) {
    CoroutineScope(Dispatchers.IO).launch {
        val apiService = ApiConnections.RetrofitClient.getInstance(context)
        val authToken = UserPreferences.getUserTokenSync(context)
        val call = apiService.getUser("Bearer $authToken", userId)
        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    CoroutineScope(Dispatchers.Main).launch {
                        onResult(response.body(), null)
                    }
                } else {
                    CoroutineScope(Dispatchers.Main).launch {
                        onResult(null, response.message())
                    }
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                CoroutineScope(Dispatchers.Main).launch {
                    onResult(null, t.message)
                }
            }
        })
    }
}

fun fetchUserRecipes(context: Context, userId: Int, onResult: (List<HomeRecipe>?, String?) -> Unit) {
    CoroutineScope(Dispatchers.IO).launch {
        val apiService = ApiConnections.RetrofitClient.getInstance(context)
        val authToken = UserPreferences.getUserTokenSync(context)
        val call = apiService.getUserRecipes("Bearer $authToken", userId)
        call.enqueue(object : Callback<List<HomeRecipe>> {
            override fun onResponse(call: Call<List<HomeRecipe>>, response: Response<List<HomeRecipe>>) {
                if (response.isSuccessful) {
                    CoroutineScope(Dispatchers.Main).launch {
                        onResult(response.body(), null)
                    }
                } else {
                    CoroutineScope(Dispatchers.Main).launch {
                        onResult(null, response.message())
                    }
                }
            }

            override fun onFailure(call: Call<List<HomeRecipe>>, t: Throwable) {
                CoroutineScope(Dispatchers.Main).launch {
                    onResult(null, t.message)
                }
            }
        })
    }
}
