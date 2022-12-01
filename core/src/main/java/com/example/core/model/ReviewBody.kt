package com.example.core.model

data class ReviewBody(
    val score: Int = 0,
    val companyId: String = "",
    val comment: String = "",
    val userName: String = "",
)
