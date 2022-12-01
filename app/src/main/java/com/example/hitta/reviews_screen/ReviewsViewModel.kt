package com.example.hitta.reviews_screen

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.model.Resource
import com.example.core.utils.DispatcherProvider
import com.example.domain.model.*
import com.example.domain.usecase.GetCompanyUseCase
import com.example.domain.usecase.GetMyReviewUseCase
import com.example.domain.usecase.GetReviewsUseCase
import com.example.core.utils.EventAggregator
import com.example.core.utils.EventAggregator.Companion.COMPANY_ID
import com.example.hitta.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class ReviewsViewModel @Inject constructor(
    private val getCompanyUseCase: GetCompanyUseCase,
    private val getMyReviewUseCase: GetMyReviewUseCase,
    private val getReviewsUseCase: GetReviewsUseCase,
    private val eventAggregator: EventAggregator,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val actionDispatcher = MutableSharedFlow<Action>()

    val uiState = merge(
        getDataFlow(),
        actionDispatcher,
    ).scan(initial = ReviewsState()) { state, action ->
        action.map(state)
    }.stateIn(
        scope = viewModelScope.plus(dispatcherProvider.main),
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ReviewsState()
    )

   private fun getDataFlow(): Flow<Action> {
       return combine(
           getCompanyUseCase.invoke(COMPANY_ID),
           getMyReviewUseCase.invoke(),
           getReviewsUseCase.invoke(),
       ) { companyResource, myReviewResource, reviewsResource ->
           val items = getItems(
               companyResource = companyResource,
               myReviewResource = myReviewResource,
               reviewsResource = reviewsResource
           )
           val messageId = getMessage(
               companyResource = companyResource,
               myReviewResource = myReviewResource,
               reviewsResource = reviewsResource
           )
           arrayOf(
               Action.Items(items),
               Action.Error(messageId),
               Action.Loading(false)
           )
       }.flatMapLatest { actions -> flowOf(*actions) }
   }

    private fun getItems(
        companyResource: Resource<Company, Int>,
        myReviewResource: Resource<ReviewItem, Int>,
        reviewsResource: Resource<List<Review>, Int>
    ): List<ReviewItem> {
        return mutableListOf<ReviewItem>().apply {
            addCompanyItem(this, companyResource)
            addMyReview(this, myReviewResource)
            addReviews(this, reviewsResource)
        }
    }

    private fun addCompanyItem(
        reviewItems: MutableList<ReviewItem>,
        companyResource: Resource<Company, Int>
    ) {
        if (companyResource is Resource.Success) {
            reviewItems.add(companyResource.data)
        }
    }

    private fun addMyReview(
        reviewItems: MutableList<ReviewItem>,
        myReviewResource: Resource<ReviewItem, Int>
    ) {
        if (myReviewResource is Resource.Success) {
            reviewItems.add(myReviewResource.data)
        }
    }

    private fun addReviews(
        reviewItems: MutableList<ReviewItem>,
        reviewsResource: Resource<List<Review>, Int>
    ) {
        if (reviewsResource is Resource.Success) {
            reviewItems.add(Header(titleId = R.string.Latest_reviews))
            reviewItems.addAll(reviewsResource.data)
            reviewItems.add(TextButton(titleId = R.string.View_all_reviews))
        }
    }

    private fun getMessage(
        companyResource: Resource<Company, Int>,
        myReviewResource: Resource<ReviewItem, Int>,
        reviewsResource: Resource<List<Review>, Int>
    ): Int? {
        return when {
            companyResource is Resource.Error -> companyResource.data
            myReviewResource is Resource.Error -> myReviewResource.data
            reviewsResource is Resource.Error -> reviewsResource.data
            else -> null
        }
    }

    fun onRateClick(rating: Float) {
        eventAggregator.setScore(rating.toInt())
    }

    fun onMessageShown() {
        viewModelScope.launch(dispatcherProvider.main) {
            actionDispatcher.emit(Action.MessageShown)
        }
    }

    private sealed interface Action {
        fun map(state: ReviewsState): ReviewsState

        data class Loading(val isLoading: Boolean): Action {
            override fun map(state: ReviewsState): ReviewsState {
                return state.copy(isLoading = isLoading)
            }
        }

        data class Items(val items: List<ReviewItem>): Action {
            override fun map(state: ReviewsState): ReviewsState {
                return state.copy(items = items)
            }
        }

        data class Error(@StringRes val messageId: Int?) : Action {
            override fun map(state: ReviewsState): ReviewsState {
                return state.copy(messageId = messageId)
            }
        }

        object MessageShown: Action {
            override fun map(state: ReviewsState): ReviewsState {
                return state.copy(messageId = null)
            }
        }
    }
}