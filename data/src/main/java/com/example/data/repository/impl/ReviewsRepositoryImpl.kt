package com.example.data.repository.impl

import com.example.core.model.Resource
import com.example.data.datasource.ReviewsDataSource
import com.example.data.model.ReviewBodyDto
import com.example.data.model.ReviewDto
import com.example.data.repository.ReviewsRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReviewsRepositoryImpl @Inject constructor(
    private val reviewsDataSource: ReviewsDataSource,
) : ReviewsRepository {

    private val reviews = MutableSharedFlow<List<ReviewDto>>(replay = 1)

    override fun getMyReview(): Flow<ReviewDto> {
        return reviews
            .map { list -> list.filter { it.id == "myReview" } }
            .map { list -> list.firstOrNull() }
            .filterNotNull()
    }

    override fun loadReviews(): Flow<Throwable> {
        return flow {
            val response = reviewsDataSource.getReviews()
            when (response) {
                is Resource.Success -> cacheReviews(response.data)
                is Resource.Error -> emit(response.data)
            }
        }
    }

    private suspend fun cacheReviews(list: List<ReviewDto>) {
        reviews.emit(list)
    }

    override fun getReviews(): Flow<List<ReviewDto>> {
        return reviews.map { list -> list.filter { it.id != "myReview" } }
    }

    override suspend fun saveReview(reviewBodyDto: ReviewBodyDto): Resource<Unit, Throwable> {
        return reviewsDataSource.submitReview(reviewBodyDto)
    }
}