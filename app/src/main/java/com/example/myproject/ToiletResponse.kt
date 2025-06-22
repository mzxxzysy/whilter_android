package com.example.myproject

import com.google.gson.annotations.SerializedName

data class ToiletResponse(
    val currentCount: Int,
    val data: List<ToiletInfo>,
    val matchCount: Int? = null,
    val page: Int? = null,
    val perPage: Int? = null,
    val totalCount: Int? = null
)

data class ToiletInfo(
    @SerializedName("연번") val id: Int,
    @SerializedName("운영노선명") val line: String,
    @SerializedName("역명") val stationName: String,
    @SerializedName("상세위치") val location: String,
    @SerializedName("위도") val latitude: String,
    @SerializedName("경도") val longitude: String
)