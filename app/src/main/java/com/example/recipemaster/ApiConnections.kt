package com.example.recipemaster

import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

class ApiConnections {
    interface ApiService {
        @POST("auth/login")
        fun login(@Body request: LoginRequest): Call<LoginResponse>
        @POST("auth/register")
        fun signUp(@Body request: SignUpRequest): Call<SignUpResponse>
        @GET("recipes")
        fun getRecipes(): Call<List<HomeRecipe>>
    }

    object RetrofitClient {
        private const val BASE_URL = "http://192.168.0.157:8000/api/"

        val instance: ApiService by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpClient.Builder().build())
                .build()

            retrofit.create(ApiService::class.java)
        }
    }

}