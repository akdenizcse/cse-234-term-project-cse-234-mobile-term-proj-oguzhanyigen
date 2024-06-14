// CreatePage.kt
package com.example.recipemaster

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import coil.compose.rememberImagePainter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MediaType.Companion.parse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePage(onRecipeCreated: (Boolean, String?) -> Unit) {
    var title by remember { mutableStateOf("") }
    var ingredients by remember { mutableStateOf("") }
    var instructions by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var loading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current
    val userId = runBlocking { UserPreferences.getUserId(context).first() } ?: 0

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            imageUri = uri
        }
    )

    fun createRecipe(
        title: String,
        instructions: String,
        ingredients: String,
        userId: Int,
        imageUri: Uri?,
        onResponse: (Boolean, String?) -> Unit
    ) {
        val titlePart = title.toRequestBody(MultipartBody.FORM)
        val instructionsPart = instructions.toRequestBody(MultipartBody.FORM)
        val ingredientsPart = ingredients.toRequestBody(MultipartBody.FORM)
        val userIdPart = userId.toString().toRequestBody(MultipartBody.FORM)

        val imagePart = imageUri?.let {
            val inputStream = context.contentResolver.openInputStream(it)
            val byteArray = inputStream?.readBytes()
            val requestBody = RequestBody.create("image/*".toMediaTypeOrNull(), byteArray!!)
            MultipartBody.Part.createFormData("image", "image.jpg", requestBody)
        }

        CoroutineScope(Dispatchers.IO).launch {
            val authToken = UserPreferences.getUserTokenSync(context)
            if (authToken != null) {
                val apiService = ApiConnections.RetrofitClient.getInstance(context)
                val call = apiService.createRecipe("Bearer $authToken", titlePart, instructionsPart, ingredientsPart, userIdPart, imagePart)
                call.enqueue(object : Callback<CreateRecipeResponse> {
                    override fun onResponse(
                        call: Call<CreateRecipeResponse>,
                        response: Response<CreateRecipeResponse>
                    ) {
                        if (response.isSuccessful) {
                            CoroutineScope(Dispatchers.Main).launch {
                                val createResponse = response.body()
                                if (createResponse != null) {
                                    onResponse(createResponse.success, createResponse.message)
                                } else {
                                    onResponse(false, response.message())
                                }
                            }
                        } else {
                            CoroutineScope(Dispatchers.Main).launch {
                                onResponse(false, response.message())
                            }
                        }
                    }

                    override fun onFailure(call: Call<CreateRecipeResponse>, t: Throwable) {
                        CoroutineScope(Dispatchers.Main).launch {
                            onResponse(false, t.message)
                        }
                    }
                })
            } else {
                CoroutineScope(Dispatchers.Main).launch {
                    onResponse(false, "Authentication token not found")
                }
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

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { launcher.launch("image/*") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Select Image")
        }

        imageUri?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = rememberImagePainter(data = it),
                contentDescription = null,
                modifier = Modifier.size(128.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                loading = true
                createRecipe(title, instructions, ingredients, userId, imageUri) { success, message ->
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
