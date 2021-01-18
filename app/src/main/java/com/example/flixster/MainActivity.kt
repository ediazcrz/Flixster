package com.example.flixster

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.example.flixster.models.Movie
import com.google.gson.Gson
import okhttp3.Headers
import org.json.JSONException

const val NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed"
const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    var movies = listOf<Movie>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val client = AsyncHttpClient()
        client.get(NOW_PLAYING_URL, object: JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                Log.d(TAG, "onSuccess")
                val jsonObject = json.jsonObject

                try {
                    val results = jsonObject.getJSONArray("results")
                    Log.i(TAG, "Results: $results")

                    movies = Gson().fromJson(results.toString(), Array<Movie>::class.java).toList()
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