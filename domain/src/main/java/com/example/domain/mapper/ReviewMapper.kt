package com.example.domain.mapper

import com.example.core.mapper.Mapper
import com.example.data.model.ReviewDto
import com.example.domain.model.Review
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReviewMapper @Inject constructor(): Mapper<ReviewDto, Review> {

    override fun map(objectToMap: ReviewDto): Review {
        return Review(
            id =objectToMap.id.orEmpty(),
            authorAvatar = objectToMap.authorAvatar,
            authorName = objectToMap.authorName.orEmpty(),
            rating = objectToMap.rating ?: 0,
            timestamp = objectToMap.timestamp.orEmpty(),
            ratingDomain = objectToMap.ratingDomain.orEmpty(),
            description = objectToMap.description.orEmpty(),
        )
    }
}