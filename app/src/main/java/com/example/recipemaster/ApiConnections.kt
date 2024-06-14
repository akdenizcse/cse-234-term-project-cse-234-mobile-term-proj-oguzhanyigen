// ApiConnections.kt
package com.example.recipemaster

import android.content.Context
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


// Define data class for Recipe
data class HomeRecipe(
    val id: Int,
    val title: String,
    val ingredients: String,
    val instructions: String
)

data class CreateRecipeRequest(
    val title: String,
    val instructions: String,
    val ingredients: String
)

data class CreateRecipeResponse(
    val success: Boolean,
    val message: String?
)
// User data class
data class User(val id: Int, val name: String, val email: String)

class ApiConnections {

    interface ApiService {
        @POST("auth/login")
        fun login(@Body request: LoginRequest): Call<LoginResponse>

        @POST("auth/register")
        fun signUp(@Body request: SignUpRequest): Call<SignUpResponse>

        @GET("recipes")
        fun getRecipes(): Call<List<HomeRecipe>>

        @GET("recipes/{id}")
        fun getRecipeById(@Path("id") id: Int): Call<HomeRecipe>

        @POST("recipes")
        fun createRecipe(
            @Header("Authorization") authToken: String,
            @Body request: CreateRecipeRequest
        ): Call<CreateRecipeResponse>

        @POST("favorites")
        fun addFavorite(
            @Header("Authorization") authToken: String,
            @Query("recipe_id") recipeId: Int
        ): Call<Void>

        @DELETE("favorites/{id}")
        fun removeFavorite(
            @Header("Authorization") authToken: String,
            @Path("id") recipeId: Int
        ): Call<Void>

        @GET("favorites")
        fun getFavorites(@Header("Authorization") authToken: String): Call<List<Int>>

        @GET("user/{userId}")
        fun getUser(@Header("Authorization") authToken: String, @retrofit2.http.Path("userId") userId: Int): Call<User>
    }

    class AuthInterceptor(private val context: Context) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request: Request = chain.request()
            val token = runBlocking { UserPreferences.getUserTokenSync(context) }
            val newRequest: Request = request.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            return chain.proceed(newRequest)
        }
    }

    object RetrofitClient {
        private const val BASE_URL = "http://192.168.0.157:8000/api/"

        fun getInstance(context: Context): ApiService {
            val client = OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor(context))
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}