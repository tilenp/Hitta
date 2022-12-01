package com.example.domain.usecase.impl

import com.example.core.mapper.Mapper
import com.example.core.model.Resource
import com.example.data.model.ReviewDto
import com.example.data.repository.ReviewsRepository
import com.example.domain.model.Review
import com.example.domain.usecase.ErrorMessageUseCase
import com.example.domain.usecase.GetReviewsUseCase
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@FlowPreview
class GetReviewsUseCaseImpl @Inject constructor(
    private val reviewsRepository: ReviewsRepository,
    private val errorMessageUseCase: ErrorMessageUseCase,
    private val reviewMapper: Mapper<ReviewDto, Review>
) : GetReviewsUseCase {

    override fun invoke(): Flow<Resource<List<Review>, Int>> {
        return flowOf(
            reviewsRepository.loadReviews()
                .map { throwable -> errorMessageUseCase.invoke(throwable) }
                .map { messageId -> Resource.Error(messageId) }
                .catch { /* should not happen log the exception */ },
            reviewsRepository.getReviews()
                .map { reviewDtos -> reviewDtos.map { reviewMapper.map(it) } }
                .map { reviews -> Resource.Success(reviews) }
                .catch { /* should not happen log the exception */ }
        ).flattenConcat()
    }
}