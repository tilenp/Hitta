package com.example.hitta.reviews_screen.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.TextButton
import com.example.hitta.R
import com.example.hitta.databinding.ViewHolderTextButtonBinding

class TextButtonViewHolder(
    private val binding: ViewHolderTextButtonBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(textButton: TextButton) {
        binding.buttonTextView.setText(textButton.titleId)
    }

    companion object {
        fun create(parent: ViewGroup): TextButtonViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_text_button, parent, false)
            val binding = ViewHolderTextButtonBinding.bind(view)
            return TextButtonViewHolder(binding = binding)
        }
    }
}