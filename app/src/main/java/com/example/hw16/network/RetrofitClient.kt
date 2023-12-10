package com.example.hw16.network

import com.example.hw16.models.AuthRequestModel
import com.example.hw16.models.LoginResponseModel
import com.example.hw16.models.RegisterResponseModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

object RetrofitClient {

    private const val BASE_URL = "https://reqres.in/api/"
    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    private val retrofitBuilder by lazy {
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }
    val authService: AuthService = retrofitBuilder.create(AuthService::class.java)
}

interface AuthService {
    @POST("login")
    suspend fun login(@Body body: AuthRequestModel): Response<LoginResponseModel>

    @POST("register")
    suspend fun register(@Body body: AuthRequestModel): Response<RegisterResponseModel>
}