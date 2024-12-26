package com.example.myapplication.modules

import com.example.myapplication.util.ApiResponse
import com.example.practice.models.QuoteResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiServices {
    @GET("quotes")
    suspend fun getApiData():Response<QuoteResponse>
}