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
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST


// Define data class for Recipe
data class HomeRecipe(val id: Int, val name: String, val time: String, val rating: Float)

data class CreateRecipeRequest(
    val title: String,
    val instructions: String,
    val ingredients: String
)

data class CreateRecipeResponse(
    val success: Boolean,
    val message: String?
)

class ApiConnections {

    interface ApiService {
        @POST("auth/login")
        fun login(@Body request: LoginRequest): Call<LoginResponse>

        @POST("auth/register")
        fun signUp(@Body request: SignUpRequest): Call<SignUpResponse>

        @GET("recipes")
        fun getRecipes(): Call<List<HomeRecipe>>

        @POST("recipes")
        fun createRecipe(
            @Header("Authorization") authToken: String,
            @Body request: CreateRecipeRequest
        ): Call<CreateRecipeResponse>
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