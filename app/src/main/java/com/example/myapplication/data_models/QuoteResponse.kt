package com.example.practice.models

data class QuoteResponse(
    val limit: Int,
    val quotes: List<Quotes>,
    val skip: Int,
    val total: Int
)