package com.example.hitta.reviews_screen.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.example.domain.model.Review
import com.example.hitta.R
import com.example.hitta.databinding.ViewHolderReviewBinding
import com.example.hitta.reviews_screen.events.ReviewEvents
import com.example.core.R as coreR

class ReviewViewHolder(
    private val binding: ViewHolderReviewBinding,
    private val events: ReviewEvents,
) : RecyclerView.ViewHolder(binding.root) {

    init {
        with(binding) {
            describeExperienceTextView.setOnClickListener { events.onDescribeExperienceClick() }
        }
    }

    fun bind(review: Review) {
        setAvatar(review.authorAvatar)
        setAuthorName(review.authorName)
        setRating(review.rating)
        setRatingMetaData(review)
        setDescription(review.description)
    }

    private fun setAvatar(authorAvatar: Int?) {
        val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
        with(binding) {
            Glide.with(imageView)
                .load(authorAvatar)
                .transition(DrawableTransitionOptions.withCrossFade(factory))
                .transform(CircleCrop())
                .placeholder(coreR.drawable.anonymous)
                .error(coreR.drawable.anonymous)
                .into(imageView)
        }
    }

    private fun setAuthorName(authorName: String) {
        with(binding) {
            when {
                authorName.isNotBlank() -> authorTextView.text = authorName
                else -> authorTextView.setText(coreR.string.Anonymous)
            }
        }
    }

    private fun setRating(rating: Int) {
        binding.ratingBar.rating = rating.toFloat()
    }

    private fun setRatingMetaData(review: Review) {
        with(binding) {
            metaDataTextView.text = String.format(
                metaDataTextView.context.getString(R.string.review_meta_data_format),
                review.timestamp,
                review.ratingDomain,
            )
        }
    }

    private fun setDescription(description: String) {
        with(binding) {
            when {
                description.isNotBlank() -> {
                    describeExperienceTextView.isVisible = false
                    descriptionTextView.isVisible = true
                    descriptionTextView.text = description
                }
                else -> {
                    descriptionTextView.isVisible = false
                    describeExperienceTextView.isVisible = true
                }
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup, events: ReviewEvents): ReviewViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_review, parent, false)
            val binding = ViewHolderReviewBinding.bind(view)
            return ReviewViewHolder(binding = binding, events = events)
        }
    }
}