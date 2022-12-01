package com.example.domain.usecase

import com.example.core.model.Resource
import com.example.domain.model.ReviewItem
import kotlinx.coroutines.flow.Flow

interface GetMyReviewUseCase {

    operator fun invoke(): Flow<Resource<ReviewItem, Int>>
}