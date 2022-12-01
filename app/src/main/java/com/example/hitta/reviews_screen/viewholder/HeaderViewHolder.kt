package com.example.hitta.reviews_screen.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Header
import com.example.hitta.R
import com.example.hitta.databinding.ViewHolderHeaderBinding

class HeaderViewHolder(
    private val binding: ViewHolderHeaderBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(header: Header) {
        with(binding) {
            headerTextView.setText(header.titleId)
        }
    }

    companion object {
        fun create(parent: ViewGroup): HeaderViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_header, parent, false)
            val binding = ViewHolderHeaderBinding.bind(view)
            return HeaderViewHolder(binding = binding)
        }
    }
}