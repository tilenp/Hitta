package com.example.data.datasource

import com.example.core.model.Resource
import com.example.data.model.ReviewBodyDto
import com.example.data.model.ReviewDto

interface ReviewsDataSource {

    suspend fun getReviews(): Resource<List<ReviewDto>, Throwable>

    suspend fun submitReview(reviewBodyDto: ReviewBodyDto): Resource<Unit, Throwable>
}