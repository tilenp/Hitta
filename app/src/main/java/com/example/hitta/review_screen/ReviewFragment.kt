package com.example.hitta.review_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar.OnRatingBarChangeListener
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.core.utils.hideKeyboard
import com.example.hitta.R
import com.example.hitta.databinding.FragmentReviewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ReviewFragment : Fragment() {

    private var _binding: FragmentReviewBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ReviewViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReviewBinding.inflate(inflater, container, false)
        val view = binding.root
        setUpUI()
        return view
    }

    private fun setUpUI() {
        with(binding) {
            ratingBar.onRatingBarChangeListener =
                OnRatingBarChangeListener { _, rating, fromUser ->
                    if (fromUser) {
                        viewModel.onRateClick(rating)
                    }
                }
            closeTextView.setOnClickListener { onCloseClick() }
            saveTextView.setOnClickListener { onSaveClick() }
        }
    }

    private fun onCloseClick() {
        view?.rootView?.hideKeyboard()
        viewModel.setName(binding.nameEditText.text.toString())
        viewModel.setComment(binding.commentEditText.text.toString())
        findNavController().popBackStack()
    }

    private fun onSaveClick() {
        view?.rootView?.hideKeyboard()
        viewModel.setName(binding.nameEditText.text.toString())
        viewModel.setComment(binding.commentEditText.text.toString())
        viewModel.onSaveClick()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    updateUUI(uiState)
                }
            }
        }
    }

    private fun updateUUI(uiState: ReviewState) {
        showLoading(uiState.isLoading)
        showCompanyName(uiState.companyName)
        showRating(uiState.rating)
        preFillName(uiState.name)
        preFillComment(uiState.comment)
        showReviewSavedDialog(uiState.reviewSavedDialog)
        showMessage(uiState.messageId)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
    }

    private fun showCompanyName(companyName: String) {
        binding.companyNameTextView.text = companyName
    }

    private fun showRating(rating: Int) {
        with(binding) {
            ratingBar.rating = rating.toFloat()
            val ratingText = resources.getStringArray(R.array.rating_text)
            if (rating > 0 && rating <= ratingText.size) {
                ratingTextView.text = ratingText[(rating - 1)]
            }
        }
    }

    private fun preFillName(name: String) {
        with(binding) {
            if (nameEditText.text.toString().isEmpty()) {
                nameEditText.setText(name)
            }
        }
    }

    private fun preFillComment(comment: String) {
        with(binding) {
            if (commentEditText.text.toString().isEmpty()) {
                commentEditText.setText(comment)
            }
        }
    }

    private fun showReviewSavedDialog(dialog: ReviewSavedDialog?) {
        dialog?.let {
            AlertDialog.Builder(requireContext())
                .setCancelable(false)
                .setTitle(dialog.titleId)
                .setMessage(dialog.bodyId)
                .setPositiveButton(dialog.buttonTextId) { dialog, _ ->
                    dialog.dismiss()
                    findNavController().popBackStack()
                }
                .create()
                .show()
        }
    }

    private fun showMessage(@StringRes messageId: Int?) {
        messageId?.let {
            viewModel.onMessageShown()
            Toast.makeText(requireContext(), getString(messageId), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}