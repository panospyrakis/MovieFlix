package com.spyrakis.movieflix.features.movielist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.spyrakis.movieflix.databinding.LoaderItemBinding
import com.spyrakis.movieflix.databinding.MovieItemBinding
import com.spyrakis.movieflix.domain.models.Movie


class MovieAdapter(
    val bottomReached: () -> Unit,
    private val favouriteCallback: (Int, Boolean) -> Unit,
    private val onItemSelected: (Int) -> Unit
) : ListAdapter<MovieListItem, RecyclerView.ViewHolder>(DiffCallback) {
    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<MovieListItem>() {
            override fun areItemsTheSame(
                oldItem: MovieListItem, newItem: MovieListItem
            ): Boolean {
                if (oldItem is MovieListItem.Movie && newItem is MovieListItem.Movie) {
                    return oldItem.movie.id == newItem.movie.id
                }
                return oldItem is MovieListItem.Loading && newItem is MovieListItem.Loading
            }

            override fun areContentsTheSame(
                oldItem: MovieListItem, newItem: MovieListItem
            ): Boolean {

                if (oldItem is MovieListItem.Movie && newItem is MovieListItem.Movie) {
                    return oldItem.movie.id == newItem.movie.id && oldItem.movie.title == newItem.movie.title && oldItem.movie.poster == newItem.movie.poster && oldItem.movie.releaseDate == newItem.movie.releaseDate
                }
                return oldItem is MovieListItem.Loading && newItem is MovieListItem.Loading
            }
        }

        const val TYPE_MOVIE = 0
        const val TYPE_LOADER = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        TYPE_MOVIE -> {
            MovieViewHolder(
                MovieItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                ), favouriteCallback, onItemSelected
            )
        }

        else -> LoaderViewHolder(
            LoaderItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemViewType(position: Int) = when (getItem(position)) {
        is MovieListItem.Loading -> TYPE_LOADER
        is MovieListItem.Movie -> TYPE_MOVIE
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MovieViewHolder) {
            holder.bind((getItem(position) as MovieListItem.Movie).movie)
        }
    }

    private fun showLoading() {
        val mutableList = currentList.toMutableList()
        mutableList.add(MovieListItem.Loading)
        submitList(mutableList)
    }


    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val isLoading =
                    currentList.isNotEmpty() && currentList.last() is MovieListItem.Loading
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount
                if (!isLoading && totalItemCount == lastVisibleItemPosition + 1) {
                    showLoading()
                    bottomReached()
                }
            }
        })
    }

    inner class MovieViewHolder(
        private val binding: MovieItemBinding,
        private val favouriteCallback: (Int, Boolean) -> Unit,
        private val onItemSelected: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            with(binding) {
                title.text = movie.title
                poster.clipToOutline = true
                Glide.with(root).load(movie.getPosterUrl()).into(poster)
                rating.rating = movie.rating?.toFloat()?.div(2f) ?: 0f
                date.text = movie.releaseDate
                favouriteBtn.isSelected = movie.isFavourite == true
                favouriteBtn.setOnClickListener {
                    val newValue = !favouriteBtn.isSelected
                    favouriteCallback(movie.id, movie.isFavourite)
                    favouriteBtn.isSelected = newValue
                }
                root.setOnClickListener { onItemSelected(movie.id) }
            }
        }

    }

    inner class LoaderViewHolder(view: LoaderItemBinding) : RecyclerView.ViewHolder(view.root)
}