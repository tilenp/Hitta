package com.example.domain.usecase

import com.example.core.model.Resource
import com.example.domain.model.Review
import kotlinx.coroutines.flow.Flow

interface GetReviewsUseCase {

    operator fun invoke(): Flow<Resource<List<Review>, Int>>
}