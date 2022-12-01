package com.example.domain.usecase.impl

import com.example.core.mapper.Mapper
import com.example.core.model.Resource
import com.example.data.model.ReviewBodyDto
import com.example.data.repository.ReviewsRepository
import com.example.core.model.ReviewBody
import com.example.domain.usecase.ErrorMessageUseCase
import com.example.domain.usecase.SaveReviewUseCase
import javax.inject.Inject

class SaveReviewUseCaseImpl @Inject constructor(
    private val reviewsRepository: ReviewsRepository,
    private val reviewBodyDtoMapper: Mapper<ReviewBody, ReviewBodyDto>,
    private val errorMessageUseCase: ErrorMessageUseCase,
) : SaveReviewUseCase {

    override suspend fun saveReview(reviewBody: ReviewBody): Resource<Unit, Int> {
        val reviewBodyDto = reviewBodyDtoMapper.map(reviewBody)
        val resource = reviewsRepository.saveReview(reviewBodyDto)
        return when (resource) {
            is Resource.Success -> Resource.Success(Unit)
            is Resource.Error -> Resource.Error(errorMessageUseCase.invoke(resource.data))
        }
    }
}