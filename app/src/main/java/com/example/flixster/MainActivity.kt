package com.example.flixster

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.example.flixster.adapters.MovieAdapter
import com.example.flixster.databinding.ActivityMainBinding
import com.example.flixster.models.Movie
import com.google.gson.Gson
import okhttp3.Headers
import org.json.JSONException

const val NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed"
const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    val movies = arrayListOf<Movie>()
    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        // Create the adapter
        val movieAdapter = MovieAdapter(this, movies)

        // Set the adapter on the recycler view
        val rvMovies: RecyclerView = mainBinding.rvMovies
        rvMovies.adapter = movieAdapter

        // Set the Layout Manager on the recycler view
        rvMovies.layoutManager = LinearLayoutManager(this)

        val client = AsyncHttpClient()
        client.get(NOW_PLAYING_URL, object: JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                Log.d(TAG, "onSuccess")
                val jsonObject = json.jsonObject

                try {
                    val results = jsonObject.getJSONArray("results")
                    Log.i(TAG, "Results: $results")

                    movies.addAll(Gson().fromJson(results.toString(), Array<Movie>::class.java).toList())
                    movieAdapter.notifyDataSetChanged()
                    Log.i(TAG, "Movies: ${movies.size}")
                } catch (e: JSONException) {
                    Log.e(TAG, "Hit json exception", e)
                }
            }

            override fun onFailure(statusCode: Int, headers: Headers, response: String, throwable: Throwable) {
                Log.d("MainActivity", "onFailure")
            }
        })
    }
}