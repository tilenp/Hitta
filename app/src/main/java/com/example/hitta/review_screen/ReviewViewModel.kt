package com.example.hitta.review_screen

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.model.Resource
import com.example.core.utils.DispatcherProvider
import com.example.core.model.ReviewBody
import com.example.domain.usecase.GetCompanyUseCase
import com.example.domain.usecase.SaveReviewUseCase
import com.example.core.utils.EventAggregator
import com.example.core.utils.EventAggregator.Companion.COMPANY_ID
import com.example.hitta.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import javax.inject.Inject
import com.example.core.R as coreR

@ExperimentalCoroutinesApi
@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val getCompanyUseCase: GetCompanyUseCase,
    private val saveReviewUseCase: SaveReviewUseCase,
    private val eventAggregator: EventAggregator,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val eventDispatcher = MutableSharedFlow<Event>()
    private val actionDispatcher = MutableSharedFlow<Action>()

    val uiState = merge(
        getCompanyFlow(),
        getContentFlow(),
        getEventFlow(),
        actionDispatcher,
    ).scan(initial = ReviewState()) { state, action ->
        action.map(state)
    }.stateIn(
        scope = viewModelScope.plus(dispatcherProvider.main),
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ReviewState()
    )

    private fun getCompanyFlow(): Flow<Action> {
        return getCompanyUseCase.invoke(COMPANY_ID)
            .map { resource ->
                when (resource) {
                    is Resource.Success -> Action.Company(resource.data.displayName)
                    is Resource.Error -> Action.Error(resource.data)
                }
            }
    }

    private fun getContentFlow(): Flow<Action> {
        return eventAggregator.reviewBody
            .map { reviewBody -> Action.Content(reviewBody = reviewBody) }
    }

    private fun getEventFlow(): Flow<Action> {
        return eventDispatcher.flatMapLatest { event ->
            when (event) {
                is Event.SaveReview -> getSaveReviewFlow()
            }
        }
    }

    private fun getSaveReviewFlow(): Flow<Action> {
        return eventAggregator.reviewBody
            .take(1)
            .flatMapLatest { reviewBody ->
                flow { emit(saveReviewUseCase.saveReview(reviewBody = reviewBody)) }
                    .map { resource ->
                        when (resource) {
                            is Resource.Success -> Action.ReviewSaved
                            is Resource.Error -> Action.Error(resource.data)
                        }
                    }.onStart { emit(Action.Loading) }
            }
    }

    fun onRateClick(rating: Float) {
        eventAggregator.setScore(score = rating.toInt())
    }

    fun setName(name: String) {
        eventAggregator.setUserName(userName = name)
    }

    fun setComment(comment: String) {
        eventAggregator.setComment(comment = comment)
    }

    fun onSaveClick() {
        viewModelScope.launch(dispatcherProvider.main) {
            eventDispatcher.emit(Event.SaveReview)
        }
    }

    fun onMessageShown() {
        viewModelScope.launch(dispatcherProvider.main) {
            actionDispatcher.emit(Action.MessageShown)
        }
    }

    private sealed interface Event {
        object SaveReview : Event
    }

    private sealed interface Action {
        fun map(state: ReviewState): ReviewState

        data class Company(val companyName: String) : Action {
            override fun map(state: ReviewState): ReviewState {
                return state.copy(companyName = companyName)
            }
        }

        data class Content(val reviewBody: ReviewBody) : Action {
            override fun map(state: ReviewState): ReviewState {
                return state.copy(
                    rating = reviewBody.score,
                    name = reviewBody.userName,
                    comment = reviewBody.comment
                )
            }
        }

        object Loading : Action {
            override fun map(state: ReviewState): ReviewState {
                return state.copy(isLoading = true)
            }
        }

        object ReviewSaved : Action {
            override fun map(state: ReviewState): ReviewState {
                return state.copy(
                    isLoading = false,
                    reviewSavedDialog = ReviewSavedDialog(
                        titleId = R.string.Thank_you_for_your_review,
                        bodyId = R.string.You_re_helping_others_make_smarter_decisions_every_day,
                        buttonTextId = coreR.string.Okay
                    )
                )
            }
        }

        data class Error(@StringRes val messageId: Int) : Action {
            override fun map(state: ReviewState): ReviewState {
                return state.copy(
                    isLoading = false,
                    messageId = messageId
                )
            }
        }

        object MessageShown: Action {
            override fun map(state: ReviewState): ReviewState {
                return state.copy(messageId = null)
            }
        }
    }
}