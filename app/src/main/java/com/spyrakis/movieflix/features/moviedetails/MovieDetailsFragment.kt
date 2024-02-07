package com.spyrakis.movieflix.features.moviedetails

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.spyrakis.movieflix.R
import com.spyrakis.movieflix.databinding.FragmentMovieDetailsBinding
import com.spyrakis.movieflix.domain.models.MovieDetails
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber


const val MOVIE_ID = "MOVIE_ID"

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private val viewModel by viewModels<MovieDetailsViewModel>()
    private lateinit var binding: FragmentMovieDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initObservers()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return FragmentMovieDetailsBinding.inflate(inflater, container, false)
            .also { binding -> this.binding = binding }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initObservers() {
        viewModel.setId(arguments?.getInt(MOVIE_ID) ?: 0)
        lifecycle.coroutineScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiStateFlow.collect {
                    when (it) {
                        MovieDetailsState.Error.Details -> setErrorDetailsState()
                        MovieDetailsState.Error.Reviews -> setErrorReviewsState()
                        MovieDetailsState.Error.Similar -> setErrorSimilarState()
                        MovieDetailsState.Initial -> initUI()
                        is MovieDetailsState.Success.Details -> setSuccessDetailsState(it)
                        is MovieDetailsState.Success.Favourite -> setSuccessFavouriteState(it)
                        is MovieDetailsState.Success.Reviews -> setSuccessReviewsState(it)
                        is MovieDetailsState.Success.Similar -> setSuccessSimilarState(it)
                    }
                }
            }
        }
    }

    private fun setSuccessFavouriteState(state: MovieDetailsState.Success.Favourite) {
        Timber.d("Success Favourite state collected " + state.isFavourite)
        binding.favouriteBtn.isSelected = state.isFavourite
    }

    private fun setSuccessDetailsState(state: MovieDetailsState.Success.Details) {
        Timber.d("Success Details state collected")

        with(binding) {
            state.details.let { details ->
                Glide.with(root).load(details.getPosterUrl()).into(poster)
                cast.text = details.credits.cast.joinToString { it.name ?: "" }
                title.text = details.title
                subtitle.text = details.genres.joinToString { it.name ?: "" }
                releaseDate.text = details.releaseDate
                description.text = details.overview
                rating.rating = details.rating?.toFloat()?.div(2f) ?: 0f
                runtime.text = getString(
                    R.string.runtimeFormat, details.runtime.div(60), details.runtime.mod(60)
                )
                shareBtn.visibility = when (details.homepage.isNullOrEmpty()) {
                    false -> {
                        shareBtn.setOnClickListener {
                            share(details)
                        }
                        View.VISIBLE
                    }

                    true -> View.GONE
                }
            }
            favouriteBtn.setOnClickListener { viewModel.markFavourite() }
        }
    }

    private fun share(details: MovieDetails) {
        val share = Intent.createChooser(Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, details.homepage)
            putExtra(Intent.EXTRA_TITLE, "Share ${details.title} to other people")
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        }, null)
        requireActivity().startActivity(share)
    }

    private fun setSuccessReviewsState(state: MovieDetailsState.Success.Reviews) {
        Timber.d("Success Reviews state collected")
        with(binding.reviews) {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = ReviewsAdapter(state.reviews)
        }
    }

    private fun setSuccessSimilarState(state: MovieDetailsState.Success.Similar) {
        Timber.d("Success Similar state collected")
        with(binding.similar) {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = SimilarMoviesAdapter(state.similar)
        }
    }

    private fun initUI() {
        binding.favouriteBtn.setOnClickListener { viewModel.markFavourite() }
        binding.backButton.setOnClickListener { findNavController().popBackStack() }
    }

    private fun setErrorDetailsState() {
        Timber.d("Error Details state collected")

        Snackbar.make(
            binding.root, getString(R.string.failed_to_get_details), Snackbar.LENGTH_SHORT
        ).setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.red)).show()
    }

    private fun setErrorReviewsState() {
        Timber.d("Error Reviews state collected")

        Snackbar.make(
            binding.root, getString(R.string.failed_to_get_reviews), Snackbar.LENGTH_SHORT
        ).setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.red)).show()
    }

    private fun setErrorSimilarState() {
        Timber.d("Error Similar state collected")

        Snackbar.make(
            binding.root, getString(R.string.failed_to_get_similar), Snackbar.LENGTH_SHORT
        ).setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.red)).show()

    }

}