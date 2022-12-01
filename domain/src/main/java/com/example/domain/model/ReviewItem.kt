package com.example.domain.model

import androidx.annotation.StringRes
import com.example.core.R as coreR

sealed interface ReviewItem

data class Company(
    val id: String = "",
    val displayName: String = "",
    val reviews: CompanyReviews = CompanyReviews(),
) : ReviewItem

data class CompanyReviews(
    val score: Double = 0.0,
    val count: Long = 0,
)

data class RateAndReview(
    val authorAvatar: Int? = null,
    @StringRes val titleId: Int = coreR.string.empty_string,
    val hintId: Int = coreR.string.empty_string,
    val rating: Int = 0,
) : ReviewItem

data class Header(
    val titleId: Int = coreR.string.empty_string,
) : ReviewItem

data class Review(
    val id: String = "",
    val authorAvatar: Int? = null,
    val authorName: String = "",
    val rating: Int = 0,
    val timestamp: String = "",
    val ratingDomain: String = "",
    val description: String = "",
) : ReviewItem

data class TextButton(
    val titleId: Int = coreR.string.empty_string,
) : ReviewItem
