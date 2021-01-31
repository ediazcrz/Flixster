package com.example.flixster

import android.os.Bundle
import android.util.Log
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.example.flixster.databinding.ActivityDetailBinding
import com.example.flixster.models.Movie
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import okhttp3.Headers
import org.json.JSONException

var youTubeKey: String? = null
var autoStartVideo: Boolean = false


class DetailActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {

    private lateinit var detailBinding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(detailBinding.root)

        val tvTitle = detailBinding.tvTitle
        val tvOverview = detailBinding.tvOverview
        val ratingBar = detailBinding.ratingBar
        val youTubePlayerView = detailBinding.player

        val movie = intent.extras?.getParcelable<Movie>("movie")
        tvTitle.text = movie?.title
        tvOverview.text = movie?.overview
        ratingBar.rating = movie?.rating?.toFloat() ?: 0.toFloat()

        autoStartVideo = movie?.rating!! > 5.0
        val videoUrl = "https://api.themoviedb.org/3/movie/${movie?.movieId}/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed"

        val client = AsyncHttpClient()
        client.get(videoUrl, object: JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                Log.d("DetailActivity", "onSuccess")
                val jsonObject = json.jsonObject

                try {
                    val results = jsonObject.getJSONArray("results")

                    if (results.length() == 0) {
                        return
                    }

                    youTubeKey = results.getJSONObject(0).getString("key")
                    youTubePlayerView.initialize(BuildConfig.YOUTUBE_API_KEY, this@DetailActivity)

                    Log.d("DetailActivity", "youTubeKey=$youTubeKey")
                } catch (e: JSONException) {
                    Log.e("DetailActivity", "Failed to parse JSON", e)
                }
            }

            override fun onFailure(statusCode: Int, headers: Headers, response: String, throwable: Throwable) {
                Log.d("DetailActivity", "onFailure")
            }
        })
    }

    override fun onInitializationSuccess(providor: YouTubePlayer.Provider?, youTubePlayer: YouTubePlayer?, wasRestored: Boolean) {
        Log.d("DetailActivity", "onInitializationSuccess")

        if (!wasRestored) {
            if (autoStartVideo) {
                youTubePlayer?.loadVideo(youTubeKey)
            } else {
                youTubePlayer?.cueVideo(youTubeKey)
            }
        }
    }

    override fun onInitializationFailure(providor: YouTubePlayer.Provider?, youTubeInitializationResult: YouTubeInitializationResult?) {
        Log.d("DetailActivity", "onInitializationFailure")
    }
}