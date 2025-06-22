package com.example.myproject

import com.google.gson.annotations.SerializedName

data class LiftResponse(
    val currentCount: Int,
    val data: List<LiftInfo>,
    val matchCount: Int? = null,
    val page: Int? = null,
    val perPage: Int? = null,
    val totalCount: Int? = null
)

data class LiftInfo(
    @SerializedName("연번") val id: Int,
    @SerializedName("호선") val line: String,
    @SerializedName("역명") val stationName: String,
    @SerializedName("시작층(상세위치)") val location: String
)