package com.example.hitta.reviews_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.model.ReviewItem
import com.example.hitta.R
import com.example.hitta.databinding.FragmentReviewsBinding
import com.example.hitta.reviews_screen.events.RateAndReviewEvents
import com.example.hitta.reviews_screen.events.ReviewEvents
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ReviewsFragment : Fragment(), RateAndReviewEvents, ReviewEvents {

    private var _binding: FragmentReviewsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ReviewsViewModel by viewModels()
    private lateinit var reviewsAdapter: ReviewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReviewsBinding.inflate(inflater, container, false)
        val view = binding.root
        setUpUI()
        return view
    }

    private fun setUpUI() {
        reviewsAdapter = ReviewsAdapter(this, this)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(ReviewItemDecorator(requireContext()))
            adapter = reviewsAdapter
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    updateState(uiState)
                }
            }
        }
    }

    private fun updateState(uiState: ReviewsState) {
        showLoading(uiState.isLoading)
        showItems(uiState.items)
        showMessage(uiState.messageId)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
    }

    private fun showItems(items: List<ReviewItem>) {
        binding.recyclerView.isVisible = true
        reviewsAdapter.setItems(items)
    }

    private fun showMessage(@StringRes messageId: Int?) {
        messageId?.let {
            viewModel.onMessageShown()
            Toast.makeText(requireContext(), getString(messageId), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRateClick(rating: Float) {
        viewModel.onRateClick(rating = rating)
    }

    override fun onDescribeExperienceClick() {
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}