package com.ziadahmed.logintask

import MovieModel
import MoviesAdapter
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException


class MoviesActivity : AppCompatActivity() {

    var client: OkHttpClient = OkHttpClient()
    private lateinit var moviesRecyclerView: RecyclerView
    private lateinit var moviesAdapter: MoviesAdapter
    private val moviesList = mutableListOf<MovieModel>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        moviesRecyclerView = findViewById(R.id.recycler_view)
        moviesRecyclerView.layoutManager = LinearLayoutManager(this)
        moviesAdapter = MoviesAdapter(moviesList)
        moviesRecyclerView.adapter = moviesAdapter

        fetchMovies()


     }

    private fun fetchMovies() {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://yts.mx/api/v2/list_movies.json")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    val jsonResponse = JSONObject(response.body!!.string())
                    val moviesArray = jsonResponse.getJSONObject("data").getJSONArray("movies")

                    for (i in 0 until moviesArray.length()) {
                        val movieJson = moviesArray.getJSONObject(i)
                        val movie = MovieModel(
                            id = movieJson.getInt("id"),
                            title = movieJson.getString("title"),
                            year = movieJson.getInt("year"),
                            rating = movieJson.getDouble("rating"),
                            summary = movieJson.getString("summary"),
                            medium_cover_image = movieJson.getString("medium_cover_image")
                        )
                        moviesList.add(movie)
                    }

                    runOnUiThread {
                        moviesAdapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }






}