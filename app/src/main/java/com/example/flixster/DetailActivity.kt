package com.example.flixster

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.flixster.databinding.ActivityDetailBinding
import com.example.flixster.models.Movie

class DetailActivity : AppCompatActivity() {

    private lateinit var detailBinding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(detailBinding.root)

        val tvTitle = detailBinding.tvTitle
        val tvOverview = detailBinding.tvOverview
        val ratingBar = detailBinding.ratingBar

        val movie = intent.extras?.getParcelable<Movie>("movie")
        tvTitle.text = movie?.title
        tvOverview.text = movie?.overview
        ratingBar.rating = movie?.rating?.toFloat() ?: 0.toFloat()
    }
}