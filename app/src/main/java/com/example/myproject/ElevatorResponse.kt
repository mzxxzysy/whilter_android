package com.example.myproject

import com.google.gson.annotations.SerializedName

data class ElevatorResponse(
    val currentCount: Int,
    val data: List<ElevatorInfo>,
    val matchCount: Int? = null,
    val page: Int? = null,
    val perPage: Int? = null,
    val totalCount: Int? = null
)

data class ElevatorInfo(
    @SerializedName("연번") val id: Int,
    @SerializedName("호선") val line: String,
    @SerializedName("역  명") val stationName: String,
    @SerializedName("시작층(상세위치)") val location: String
)