package com.example.recipemaster

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val id: Int,
    val status: Boolean,
    val message: String,
    val token: String?
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(onLoginClick: (String?) -> Unit, onSignUpClick: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    fun login(email: String, password: String, onResponse: (Boolean, String?, Int?) -> Unit) {
        val request = LoginRequest(email, password)
        val apiService = ApiConnections.RetrofitClient.getInstance(context) // Get ApiService instance with context
        val call = apiService.login(request)

        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val loginResponse = response.body()!!
                    if (loginResponse.status) {
                        onResponse(true, loginResponse.token, loginResponse.id)
                    } else {
                        onResponse(false, loginResponse.message, null)
                    }
                } else {
                    onResponse(false, response.message(), null)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                onResponse(false, t.message, null)
            }
        })
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.loginbg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            alpha = 0.8f
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(Color.Transparent),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 16.dp, top = 80.dp)
            ) {
                Text(
                    "Hello,",
                    style = TextStyle(
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                )
                Text("Welcome Back!", style = TextStyle(fontSize = 18.sp, color = Color.DarkGray))
            }

            Spacer(modifier = Modifier.height(20.dp))

            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email", style = TextStyle(color = Color.DarkGray)) },
                placeholder = { Text("Enter Email", style = TextStyle(color = Color.DarkGray)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 1.dp, color = Color.DarkGray, shape = RoundedCornerShape(8.dp)),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email
                ),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black

                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password", style = TextStyle(color = Color.DarkGray)) },
                placeholder = { Text("Enter Password", style = TextStyle(color = Color.DarkGray)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 1.dp, color = Color.DarkGray, shape = RoundedCornerShape(8.dp)),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password
                ),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black

                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Forgot Password?",
                color = Color(0xFFFFA500),
                fontSize = 12.sp,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    loading = true
                    login(email, password) { success, token, userId ->
                        loading = false
                        if (success) {
                            // Save user ID and token to DataStore
                            CoroutineScope(Dispatchers.IO).launch {
                                userId?.let { UserPreferences.saveUserId(context, it) }
                                token?.let { UserPreferences.saveUserToken(context, it) }
                            }
                            onLoginClick(token) // Pass token to the parent composable
                        } else {
                            errorMessage = token
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(Color(0xff129575))
            ) {
                if (loading) {
                    CircularProgressIndicator(color = Color.White)
                } else {
                    Text("Sign In", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Don't have an account? Sign up",
                color = Color(0xFFFFA500),
                fontSize = 12.sp,
                modifier = Modifier.clickable(onClick = onSignUpClick)
            )

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
}
