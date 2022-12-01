package com.example.domain.usecase.impl

import com.example.core.mapper.Mapper
import com.example.core.model.Resource
import com.example.core.utils.EventAggregator
import com.example.data.model.ReviewDto
import com.example.data.repository.ReviewsRepository
import com.example.domain.model.RateAndReview
import com.example.domain.model.Review
import com.example.domain.model.ReviewItem
import com.example.domain.usecase.GetMyReviewUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import com.example.core.R as coreR

@ExperimentalCoroutinesApi
class GetMyReviewUseCaseImpl @Inject constructor(
    private val eventAggregator: EventAggregator,
    private val reviewsRepository: ReviewsRepository,
    private val reviewMapper: Mapper<ReviewDto, Review>
) : GetMyReviewUseCase {

    override fun invoke(): Flow<Resource<ReviewItem, Int>> {
        return eventAggregator.reviewBody
            .map { reviewBody -> reviewBody.score }
            .flatMapLatest { rating ->
                reviewsRepository.getMyReview()
                    .map { reviewDto -> reviewMapper.map(reviewDto) }
                    .map { review -> Resource.Success(review) }
                    .map { it as Resource.Success<ReviewItem> }
                    .onStart { emit(Resource.Success(buildRateAndReview(rating))) }
                    .catch { /* should not happen log the exception */ }
            }
    }

    private fun buildRateAndReview(rating: Int): RateAndReview {
        return RateAndReview(
            titleId = coreR.string.Rate_and_review,
            hintId = coreR.string.Share_your_experience_to_help_others,
            rating = rating,
        )
    }
}