package com.example.flixster.adapters

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.flixster.DetailActivity
import com.example.flixster.R
import com.example.flixster.adapters.MovieAdapter.MovieViewHolder
import com.example.flixster.databinding.ItemMovieBinding
import com.example.flixster.models.Movie

const val TAG = "MovieAdapter"
const val baseUrl = "http://image.tmdb.org/t/p/"

class MovieAdapter(private val context: Context, private val movies: List<Movie>): RecyclerView.Adapter<MovieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        Log.d(TAG, "onCreateViewHolder")
        val view = ItemMovieBinding.inflate(LayoutInflater.from(context), parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder $position")
        val movie: Movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    inner class MovieViewHolder(private val binding: ItemMovieBinding): ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.tvTitle.text = movie.title
            binding.tvOverview.text = movie.overview

            val orientation = context.resources.configuration.orientation
            val imageUrl = if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                "$baseUrl/w1280/${movie.backdropPath}"
            } else {
                "$baseUrl/w342/${movie.posterPath}"
            }

            Glide.with(binding.root)
                    .load(imageUrl)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.imagenotfound)
                    .centerInside()
                    .into(binding.ivPoster)

            binding.container.setOnClickListener {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("movie", movie)
                context.startActivity(intent)
            }
        }
    }
}