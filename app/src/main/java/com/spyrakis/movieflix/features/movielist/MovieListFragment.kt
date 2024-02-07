package com.spyrakis.movieflix.features.movielist

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.spyrakis.movieflix.R
import com.spyrakis.movieflix.core.navcontroller.Navigator
import com.spyrakis.movieflix.databinding.FragmentMovieListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MovieListFragment : Fragment() {

    private val viewModel by viewModels<MovieListViewModel>()

    private lateinit var binding: FragmentMovieListBinding

    private lateinit var listAdapter: MovieAdapter

    @Inject
    lateinit var appNavigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initObservers()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        appNavigator.bind(this)

        return FragmentMovieListBinding.inflate(inflater, container, false)
            .also { binding -> this.binding = binding }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInitialState()
    }

    private fun initObservers() {
        lifecycle.coroutineScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiStateFlow.collect { state ->
                    when (state) {
                        MovieListState.Error -> setErrorState()
                        MovieListState.Initial -> setInitialState()
                        is MovieListState.Success -> setSuccessState(state)
                    }
                }
            }
        }
    }

    private fun setInitialState() {
        Timber.d("Initial state collected")

        binding.swipeRefresh.setOnRefreshListener { viewModel.refresh() }
        listAdapter = MovieAdapter(
            viewModel::onBottomReached, viewModel::markFavourite, viewModel::onItemSelected
        )

        with(binding.list) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = listAdapter
        }
    }

    private fun setErrorState() {
        Timber.d("Error state collected")
        binding.swipeRefresh.isRefreshing = false
        listAdapter.submitList(listOf())
        Snackbar.make(binding.root, getString(R.string.failed_to_get_movies), Snackbar.LENGTH_SHORT)
            .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.red))
            .setAction(getString(R.string.retry)) { viewModel.onBottomReached() }.show()
    }

    private fun setSuccessState(state: MovieListState.Success) {
        Timber.d("Success state collected: " + state.list.size)
        binding.swipeRefresh.isRefreshing = false
        listAdapter.submitList(state.list)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        appNavigator.unbind()
    }
}