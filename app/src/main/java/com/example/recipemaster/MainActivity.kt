package com.example.recipemaster

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.recipemaster.ui.theme.RecipeMasterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecipeMasterTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RecipeMasterApp()
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RecipeMasterApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != "login" && currentRoute != "signup") {
                BottomBar(navController = navController)
            }
        }
    ) { innerPadding ->
        NavHost(navController = navController, startDestination = "profile",modifier = Modifier.padding(innerPadding).background(Color.White)) {
            composable("login") {
                LoginPage(
                    onLoginClick = { navController.navigate("home") },
                    onSignUpClick = { navController.navigate("signup") }
                )
            }
            composable("signup") {
                SignUpPage(
                    onSignUpClick = { navController.navigate("login") },
                    onLoginClick = { navController.navigate("login") }
                )
            }
            composable("home") { HomePage() }
            composable("favorites") { FavoritesPage() }
            composable("create") { CreatePage() }
            composable("search") { SearchPage() }
            composable("profile") {
                ProfilePage()
            }
        }
    }
}





@Composable
fun FavoritesPage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(text = "Favorites Page", style = TextStyle(fontSize = 24.sp, color = Color.Black))
    }
}

@Composable
fun CreatePage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(text = "Create Page", style = TextStyle(fontSize = 24.sp, color = Color.Black))
    }
}

@Composable
fun SearchPage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(text = "Search Page", style = TextStyle(fontSize = 24.sp, color = Color.Black))
    }
}

