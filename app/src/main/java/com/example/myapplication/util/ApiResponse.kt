package com.example.myapplication.util

sealed class ApiResponse<out T> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Failed<out T>(val message: String,val exception:Exception?=null) : ApiResponse<Nothing>()
    data object Loading:ApiResponse<Nothing>()
}