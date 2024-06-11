package com.example.recipemaster

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomBar(navController: NavController) {
    val items = listOf(
        BottomNavItem("home", Icons.Default.Home, "Home"),
        BottomNavItem("favorites", Icons.Default.Favorite, "Favorites"),
        BottomNavItem("create", Icons.Default.AddCircle, "Create"),
        BottomNavItem("search", Icons.Default.Search, "Search"),
        BottomNavItem("profile", Icons.Default.Person, "Profile")
    )

    Column {
        // Top border
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(Color.LightGray) // Set the color of the border
        )
        // Bottom navigation bar
        NavigationBar(
            containerColor = Color.White,
            contentColor = Color.Gray,
            modifier = Modifier.padding(0.dp)
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            items.forEach { item ->
                NavigationBarItem(
                    icon = {
                        Icon(imageVector = item.icon, contentDescription = item.label,
                        modifier =
                        if (item.label == "Create") {
                        Modifier.size(55.dp).absoluteOffset(y = (-10).dp)
                        }else {
                            Modifier.size(24.dp)
                        })
                    },
                    label = {
                        if (item.label == "Create") {
                            Text("")
                        }else {
                            Text(item.label)
                        }
                         },

                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF4CAF50),
                        selectedTextColor = Color(0xFF4CAF50),
                        unselectedIconColor = if (item.label == "Create") Color(0xFF4CAF50) else Color.Gray,
                        unselectedTextColor = Color.Gray,
                        indicatorColor = Color.White

                    ),
                    alwaysShowLabel = true,
                    selected = currentRoute == item.route,
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}

data class BottomNavItem(val route: String, val icon: ImageVector, val label: String)
