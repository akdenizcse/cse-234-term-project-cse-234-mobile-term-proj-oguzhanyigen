// CreatePage.kt
package com.example.recipemaster

import android.content.Context
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePage(onRecipeCreated: (Boolean, String?) -> Unit) {
    var title by remember { mutableStateOf("") }
    var ingredients by remember { mutableStateOf("") }
    var instructions by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current
    val userId = runBlocking { UserPreferences.getUserId(context).first() } ?: 0

    fun createRecipe(
        title: String,
        instructions: String,
        ingredients: String,
        userId: Int,
        onResponse: (Boolean, String?) -> Unit
    ) {
        val request = CreateRecipeRequest(
            title = title,
            instructions = instructions,
            ingredients = ingredients,
            userId = userId
        )
        CoroutineScope(Dispatchers.IO).launch {
            val authToken = UserPreferences.getUserTokenSync(context)
            if (authToken != null) {
                val apiService = ApiConnections.RetrofitClient.getInstance(context)
                val call = apiService.createRecipe("Bearer $authToken", request)
                call.enqueue(object : Callback<CreateRecipeResponse> {
                    override fun onResponse(
                        call: Call<CreateRecipeResponse>,
                        response: Response<CreateRecipeResponse>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            val createResponse = response.body()!!
                            onResponse(createResponse.success, createResponse.message)
                        } else {
                            onResponse(false, response.message())
                        }
                    }

                    override fun onFailure(call: Call<CreateRecipeResponse>, t: Throwable) {
                        onResponse(false, t.message)
                    }
                })
            } else {
                onResponse(false, "Authentication token not found")
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title", style = TextStyle(color = Color.DarkGray)) },
            placeholder = { Text("Enter Title", style = TextStyle(color = Color.DarkGray)) },
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 1.dp, color = Color.DarkGray, shape = RoundedCornerShape(8.dp)),
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text
            ),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = Color.Black
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
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text
            ),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = instructions,
            onValueChange = { instructions = it },
            label = { Text("Instructions", style = TextStyle(color = Color.DarkGray)) },
            placeholder = { Text("Enter Instructions", style = TextStyle(color = Color.DarkGray)) },
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 1.dp, color = Color.DarkGray, shape = RoundedCornerShape(8.dp)),
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text
            ),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                loading = true
                createRecipe(title, instructions, ingredients, userId) { success, message ->
                    loading = false
                    if (success) {
                        onRecipeCreated(true, "Recipe created successfully")
                    } else {
                        errorMessage = message
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(Color(0xff129575))
        ) {
            Text("Create Recipe", color = Color.White, fontSize = 16.sp)
        }

        errorMessage?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                it,
                color = Color.Red,
                fontSize = 12.sp
            )
        }
    }
}
