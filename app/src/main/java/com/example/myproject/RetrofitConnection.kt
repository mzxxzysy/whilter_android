package com.example.myproject

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitConnection {
    companion object {
        private const val BASE_URL = "https://api.odcloud.kr/"

        val jsonNetworkService: NetworkService by lazy {
            jsonRetrofit.create(NetworkService::class.java)
        }

        private val jsonRetrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}