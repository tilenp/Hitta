package com.example.hitta.review_screen

import androidx.annotation.StringRes

data class ReviewState(
    val isLoading: Boolean = false,
    val companyName: String = "",
    val rating: Int = 0,
    val name: String = "",
    val comment: String = "",
    val reviewSavedDialog: ReviewSavedDialog? = null,
    @StringRes val messageId: Int? = null
)

data class ReviewSavedDialog(
    val titleId: Int,
    val bodyId: Int,
    val buttonTextId: Int,
)
