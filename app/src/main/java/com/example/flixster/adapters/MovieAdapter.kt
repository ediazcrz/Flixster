package com.example.flixster.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.flixster.adapters.MovieAdapter.MovieViewHolder
import com.example.flixster.databinding.ItemMovieBinding
import com.example.flixster.models.Movie

class MovieAdapter(private val context: Context, private val movies: List<Movie>): RecyclerView.Adapter<MovieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = ItemMovieBinding.inflate(LayoutInflater.from(context), parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
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
            Glide.with(binding.root)
                    .load("https://image.tmdb.org/t/p/w342/${movie.posterPath}")
                    .centerInside()
                    .into(binding.ivPoster);
        }
    }
}