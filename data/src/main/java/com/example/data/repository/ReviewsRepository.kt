package com.example.data.repository

import com.example.core.model.Resource
import com.example.data.model.ReviewBodyDto
import com.example.data.model.ReviewDto
import kotlinx.coroutines.flow.Flow

interface ReviewsRepository {

    fun getMyReview(): Flow<ReviewDto>

    fun loadReviews(): Flow<Throwable>

    fun getReviews(): Flow<List<ReviewDto>>

    suspend fun saveReview(reviewBodyDto: ReviewBodyDto): Resource<Unit, Throwable>
}