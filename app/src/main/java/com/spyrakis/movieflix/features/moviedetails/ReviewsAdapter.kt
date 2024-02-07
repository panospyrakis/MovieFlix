package com.spyrakis.movieflix.features.moviedetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spyrakis.movieflix.databinding.ReviewsItemBinding
import com.spyrakis.movieflix.domain.models.Review

class ReviewsAdapter(private val reviews: List<Review>) :
    RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ReviewsViewHolder(
        ReviewsItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun getItemCount(): Int = reviews.size

    override fun onBindViewHolder(holder: ReviewsViewHolder, position: Int) {
        holder.bind(reviews[position])
    }

    inner class ReviewsViewHolder(private val binding: ReviewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(review: Review) {
            with(binding) {
                author.text = review.author
                content.text = review.content
            }
        }
    }
}