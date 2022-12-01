package com.example.domain.mapper

import com.example.core.mapper.Mapper
import com.example.data.model.ReviewBodyDto
import com.example.core.model.ReviewBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReviewBodyDtoMapper @Inject constructor(): Mapper<ReviewBody, ReviewBodyDto> {

    override fun map(objectToMap: ReviewBody): ReviewBodyDto {
        return ReviewBodyDto(
            score = objectToMap.score,
            companyId = objectToMap.companyId,
            comment = objectToMap.comment,
            userName = objectToMap.userName,
        )
    }
}