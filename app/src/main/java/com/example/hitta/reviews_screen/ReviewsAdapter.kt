package com.example.hitta.reviews_screen

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.*
import com.example.hitta.R
import com.example.hitta.reviews_screen.events.RateAndReviewEvents
import com.example.hitta.reviews_screen.events.ReviewEvents
import com.example.hitta.reviews_screen.viewholder.*

class ReviewsAdapter(
    private val rateAndReviewEvents: RateAndReviewEvents,
    private val reviewEvents: ReviewEvents,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<ReviewItem> = emptyList()

    fun setItems(items: List<ReviewItem>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is Company -> R.layout.view_holder_company_reviews
            is RateAndReview -> R.layout.view_holder_rate_and_review
            is Header -> R.layout.view_holder_header
            is Review -> R.layout.view_holder_review
            is TextButton -> R.layout.view_holder_text_button
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.view_holder_company_reviews -> CompanyReviewsViewHolder.create(parent = parent)
            R.layout.view_holder_rate_and_review -> RateAndReviewViewHolder.create(parent = parent, events = rateAndReviewEvents)
            R.layout.view_holder_header -> HeaderViewHolder.create(parent = parent)
            R.layout.view_holder_review -> ReviewViewHolder.create(parent = parent, events = reviewEvents)
            R.layout.view_holder_text_button -> TextButtonViewHolder.create(parent = parent)
            else -> throw Throwable("unknown viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        when (item) {
            is Company -> (holder as CompanyReviewsViewHolder).bind(item.reviews)
            is RateAndReview -> (holder as RateAndReviewViewHolder).bind(item)
            is Header -> (holder as HeaderViewHolder).bind(item)
            is Review -> (holder as ReviewViewHolder).bind(item)
            is TextButton -> (holder as TextButtonViewHolder).bind(item)
        }
    }

    override fun getItemCount() = items.size
}