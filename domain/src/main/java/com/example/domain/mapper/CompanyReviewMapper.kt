package com.example.domain.mapper

import com.example.core.mapper.Mapper
import com.example.data.model.CompanyReviewsDto
import com.example.domain.model.CompanyReviews
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CompanyReviewMapper @Inject constructor(): Mapper<CompanyReviewsDto?, CompanyReviews> {

    override fun map(objectToMap: CompanyReviewsDto?): CompanyReviews {
        return CompanyReviews(
            score = objectToMap?.score ?: 0.0,
            count = objectToMap?.count ?: 0L,
        )
    }
}