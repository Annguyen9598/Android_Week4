package com.example.android_week4.Data


import com.example.android_week4.Data.Dates
import com.example.android_week4.Data.Result
import com.google.gson.annotations.SerializedName

data class data(
    @SerializedName("dates")
    val dates: Dates,
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<Result>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)