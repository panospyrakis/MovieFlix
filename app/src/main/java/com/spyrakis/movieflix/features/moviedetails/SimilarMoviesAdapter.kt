package com.spyrakis.movieflix.features.moviedetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.spyrakis.movieflix.databinding.SimilarListItemBinding
import com.spyrakis.movieflix.domain.models.SimilarMovie

class SimilarMoviesAdapter(private val similarMovies: List<SimilarMovie>) :
    RecyclerView.Adapter<SimilarMoviesAdapter.SimilarMoviesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SimilarMoviesViewHolder(
        SimilarListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun getItemCount(): Int = similarMovies.size

    override fun onBindViewHolder(holder: SimilarMoviesViewHolder, position: Int) {
        holder.bind(similarMovies[position])
    }

    inner class SimilarMoviesViewHolder(private val binding: SimilarListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(similarMovie: SimilarMovie) {
            with(binding) {
                poster.clipToOutline = true
                Glide.with(root).load(similarMovie.getPosterUrl()).into(poster)
            }
        }
    }
}