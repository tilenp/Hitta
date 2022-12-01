package com.example.data.datasource.impl

import com.example.core.model.Resource
import com.example.data.api.TestHittaApi
import com.example.data.datasource.ReviewsDataSource
import com.example.data.model.ReviewBodyDto
import com.example.data.model.ReviewDto
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton
import com.example.core.R as coreR

@Singleton
class ReviewsDataSourceImpl @Inject constructor(
    private val testHittaApi: TestHittaApi
): ReviewsDataSource {

    private val reviews = mutableListOf(
        ReviewDto(
            id = "1",
            authorAvatar = coreR.drawable.anonymous,
            authorName = "Anonym",
            rating = 4,
            timestamp = "12h ago",
            ratingDomain = "hitta.se",
            description = "Liked it very much - probably one of the best thai restaurants in the city - recommended!"
        ),
        ReviewDto(
            id = "2",
            authorAvatar = coreR.drawable.anonymous,
            authorName = "Jenny Svennson",
            rating = 3,
            timestamp = "1d ago",
            ratingDomain = "hitta.se",
            description = "Maybe a bit too fast food. I personally dislike that. Good otherwise"
        ),
        ReviewDto(
            id = "3",
            authorAvatar = coreR.drawable.girl,
            authorName = "Happy56",
            rating = 5,
            timestamp = "1d ago",
            ratingDomain = "yelp.com",
            description = "Super good! Love the food!"
        ),
    )

    override suspend fun getReviews(): Resource<List<ReviewDto>, Throwable> {
        return Resource.Success(reviews)
    }

    override suspend fun submitReview(reviewBodyDto: ReviewBodyDto): Resource<Unit, Throwable> {
        return try {
            testHittaApi.saveReview(
                score = reviewBodyDto.score,
                companyId = reviewBodyDto.companyId,
                comment = reviewBodyDto.comment,
                userName = reviewBodyDto.userName
            )
            updateReviews(reviewBodyDto)
            return Resource.Success(Unit)
        } catch (throwable: Throwable) {
            Resource.Error(throwable)
        }
    }

    private fun updateReviews(reviewBodyDto: ReviewBodyDto) {
        reviews.removeAll { it.id == "myReview" }
        reviews.add(
            ReviewDto(
                id = "myReview",
                authorName = reviewBodyDto.userName,
                rating = reviewBodyDto.score,
                timestamp = "now",
                ratingDomain = "hitta.se",
                description = reviewBodyDto.comment
            )
        )
    }
}