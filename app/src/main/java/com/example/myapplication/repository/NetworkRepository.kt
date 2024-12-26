package com.example.myapplication.repository

import com.example.myapplication.modules.ApiServices
import com.example.myapplication.util.ApiResponse
import com.example.practice.models.QuoteResponse
import retrofit2.Response
import javax.inject.Inject

class NetworkRepository @Inject constructor (private val apiServices: ApiServices) {

    suspend fun getQuotes():Response<QuoteResponse>{
        return  apiServices.getApiData()
    }
}