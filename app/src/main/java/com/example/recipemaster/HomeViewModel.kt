package com.example.recipemaster
//import android.util.Log
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.recipemaster.ApiConnections
//import com.example.recipemaster.HomeRecipe
//import com.example.recipemaster.Recipe
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.launch
//import retrofit2.await
//import androidx.compose.ui.platform.LocalContext
//
//class HomeViewModel : ViewModel() {
//    private val _recipes = MutableStateFlow<List<HomeRecipe>>(emptyList())
//    val recipes: StateFlow<List<HomeRecipe>> get() = _recipes
//
//    init {
//        fetchRecipes()
//    }
//
//    private fun fetchRecipes() {
//        viewModelScope.launch {
//            try {
//                val recipeList = ApiConnections.RetrofitClient.instance.getRecipes().await()
//                _recipes.value = recipeList
//            } catch (e: Exception) {
//                Log.e("com.example.recipemaster.HomeViewModel", "Error fetching recipes", e)
//            }
//        }
//    }
//}
//
//
