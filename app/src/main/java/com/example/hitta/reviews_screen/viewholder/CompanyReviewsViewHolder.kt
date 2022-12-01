package com.example.hitta.reviews_screen.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.CompanyReviews
import com.example.hitta.R
import com.example.hitta.databinding.ViewHolderCompanyReviewsBinding

class CompanyReviewsViewHolder(
    private val binding: ViewHolderCompanyReviewsBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(companyReviews: CompanyReviews) {
        setScore(companyReviews.score)
        setReviewCount(companyReviews.count)
    }

    private fun setScore(score: Double) {
        binding.scoreTextView.text = score.toString()
    }

    private fun setReviewCount(count: Long) {
        with(binding) {
            countTextView.text = String.format(
                countTextView.context.getString(R.string.company_reviews_count_format),
                count
            )
        }
    }

    companion object {
        fun create(parent: ViewGroup): CompanyReviewsViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_company_reviews, parent, false)
            val binding = ViewHolderCompanyReviewsBinding.bind(view)
            return CompanyReviewsViewHolder(binding = binding)
        }
    }
}