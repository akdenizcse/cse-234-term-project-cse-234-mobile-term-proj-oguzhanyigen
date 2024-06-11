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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(onLoginClick: () -> Unit, onSignUpClick: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
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
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(Color.Transparent),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email",style = TextStyle( color = Color.DarkGray) )},
                placeholder = { Text("Enter Email",style = TextStyle( color = Color.DarkGray) ) },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 1.dp, color = Color.DarkGray, shape = RoundedCornerShape(8.dp)),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email
                ),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent, // Use containerColor to set the internal background color
                    focusedIndicatorColor = Color.Transparent, // Remove the underline when focused
                    unfocusedIndicatorColor = Color.Transparent // Remove the underline when unfocused
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password",style = TextStyle( color = Color.DarkGray) ) },
                placeholder = { Text("Enter Password",style = TextStyle( color = Color.DarkGray) ) },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 1.dp, color = Color.DarkGray, shape = RoundedCornerShape(8.dp)),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email
                ),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent, // Use containerColor to set the internal background color
                    focusedIndicatorColor = Color.Transparent, // Remove the underline when focused
                    unfocusedIndicatorColor = Color.Transparent // Remove the underline when unfocused
                ),
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
                onClick = onLoginClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(Color(0xff129575))
            ) {
                Text("Sign In", color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Don't have an account? Sign up",
                color = Color(0xFFFFA500),
                fontSize = 12.sp,
                modifier = Modifier.clickable(onClick = onSignUpClick)
            )
        }
    }
}
}


