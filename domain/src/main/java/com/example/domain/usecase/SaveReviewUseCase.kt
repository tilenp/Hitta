package com.example.domain.usecase

import com.example.core.model.Resource
import com.example.core.model.ReviewBody

interface SaveReviewUseCase {
    suspend fun saveReview(reviewBody: ReviewBody): Resource<Unit, Int>
}