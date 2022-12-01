package com.example.hitta.reviews_screen.viewholder

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.RateAndReview
import com.example.hitta.R
import com.example.hitta.databinding.ViewHolderRateAndReviewBinding
import com.example.hitta.reviews_screen.events.RateAndReviewEvents

@SuppressLint("ClickableViewAccessibility")
class RateAndReviewViewHolder(
    private val binding: ViewHolderRateAndReviewBinding,
    private val events: RateAndReviewEvents,
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var rateAndReview: RateAndReview

    init {
        with(binding) {
            ratingBar.setOnTouchListener(View.OnTouchListener { v, event ->
                val result = ratingBar.onTouchEvent(event)
                if (event.action == MotionEvent.ACTION_UP) {
                    events.onRateClick(rating = ratingBar.rating)
                }
                return@OnTouchListener result
            })
        }
    }

    fun bind(rateAndReview: RateAndReview) {
        this.rateAndReview = rateAndReview
        with(binding) {
            titleTextView.text = titleTextView.context.getString(rateAndReview.titleId)
            hintTextView.text = hintTextView.context.getString(rateAndReview.hintId)
            ratingBar.rating = rateAndReview.rating.toFloat()
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            events: RateAndReviewEvents,
        ): RateAndReviewViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_rate_and_review, parent, false)
            val binding = ViewHolderRateAndReviewBinding.bind(view)
            return RateAndReviewViewHolder(binding = binding, events)
        }
    }
}