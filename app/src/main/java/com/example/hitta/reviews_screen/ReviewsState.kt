package com.example.hitta.reviews_screen

import androidx.annotation.StringRes
import com.example.domain.model.ReviewItem
import javax.annotation.concurrent.Immutable

@Immutable
data class ReviewsState (
    val isLoading: Boolean = true,
    val items: List<ReviewItem> = emptyList(),
    @StringRes val messageId: Int? = null,
)