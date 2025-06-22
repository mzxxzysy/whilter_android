package com.example.myproject

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {
    @GET("api/15044261/v1/uddi:0900e52a-7df1-4e05-af42-98f8434701c0/")
    fun getElevatorList(
        @Query("page") page: Int = 1,
        @Query("perPage") perPage: Int = 900,
        @Query("serviceKey") serviceKey: String,
        @Query("returnType") returnType: String = "json"
    ): Call<ElevatorResponse>

    @GET("api/15044262/v1/uddi:05f3da0c-4a48-4c89-9688-ef07f17e8954")
    fun getLiftList(
        @Query("page") page: Int = 1,
        @Query("perPage") perPage: Int = 100,
        @Query("serviceKey") serviceKey: String,
        @Query("returnType") returnType: String = "json"
    ): Call<LiftResponse>

    @GET("api/15118705/v1/uddi:00ea1afb-8908-4051-a8a1-d10f532b53a6")
    fun getToiletList(
        @Query("page") page: Int = 1,
        @Query("perPage") perPage: Int = 400,
        @Query("serviceKey") serviceKey: String,
        @Query("returnType") returnType: String = "json"
    ): Call<ToiletResponse>
}