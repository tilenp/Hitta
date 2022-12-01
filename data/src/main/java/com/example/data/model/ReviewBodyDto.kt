package com.example.data.model

data class ReviewBodyDto(
    val score: Int,
    val companyId: String,
    val comment: String,
    val userName: String,
)
