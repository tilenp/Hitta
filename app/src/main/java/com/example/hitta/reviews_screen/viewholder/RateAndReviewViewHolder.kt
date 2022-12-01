package com.example.hitta.reviews_screen.viewholder

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.example.domain.model.RateAndReview
import com.example.hitta.R
import com.example.hitta.databinding.ViewHolderRateAndReviewBinding
import com.example.hitta.reviews_screen.events.RateAndReviewEvents
import com.example.core.R as coreR

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
        setAvatar()
        setTitle()
        setHint()
        setRating()
    }

    private fun setAvatar() {
        val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
        with(binding) {
            Glide.with(imageView)
                .load(rateAndReview.authorAvatar)
                .transition(DrawableTransitionOptions.withCrossFade(factory))
                .transform(CircleCrop())
                .placeholder(coreR.drawable.anonymous)
                .error(coreR.drawable.anonymous)
                .into(imageView)
        }
    }

    private fun setTitle() {
        with(binding) {
            titleTextView.text = titleTextView.context.getString(rateAndReview.titleId)
        }
    }

    private fun setHint() {
        with(binding) {
            hintTextView.text = hintTextView.context.getString(rateAndReview.hintId)
        }
    }

    private fun setRating() {
        binding.ratingBar.rating = rateAndReview.rating.toFloat()
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