package com.example.core.utils

import com.example.core.model.ReviewBody
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventAggregator @Inject constructor() {

    private val _reviewBody = MutableStateFlow(ReviewBody(companyId = COMPANY_ID))
    val reviewBody: StateFlow<ReviewBody> = _reviewBody

    fun setScore(score: Int) {
        _reviewBody.update { it.copy(score = score) }
    }

    fun setUserName(userName: String) {
        _reviewBody.update { it.copy(userName = userName) }
    }

    fun setComment(comment: String) {
        _reviewBody.update { it.copy(comment = comment) }
    }

    companion object {
        val COMPANY_ID = "ctyfiintu"
    }
}