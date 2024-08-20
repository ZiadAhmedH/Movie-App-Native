package com.ziadahmed.logintask

import MovieModel
import MoviesAdapter
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class MoviesActivity : AppCompatActivity() {

    private val client: OkHttpClient = OkHttpClient()
    private lateinit var moviesRecyclerView: RecyclerView
    private lateinit var moviesAdapter: MoviesAdapter
    private val moviesList = mutableListOf<MovieModel>()

    private var currentPage = 1
    private var isLoading = false
    private var isLastPage = false
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        moviesRecyclerView = findViewById(R.id.recycler_view)
        val layoutManager = GridLayoutManager(this, 2)
        moviesRecyclerView.layoutManager = layoutManager
        moviesAdapter = MoviesAdapter(moviesList)
        moviesRecyclerView.adapter = moviesAdapter

        moviesRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!isLoading && !isLastPage) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= moviesList.size) {
                        loadMoreMovies()
                    }
                }
            }
        })

        fetchMovies()
    }

    private fun fetchMovies() {
        isLoading = true
        showProgressBar()

        val request = Request.Builder()
            .url("https://yts.mx/api/v2/list_movies.json?page=$currentPage")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                isLoading = false
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    val jsonResponse = JSONObject(response.body!!.string())
                    val moviesArray = jsonResponse.getJSONObject("data").getJSONArray("movies")

                    if (moviesArray.length() == 0) {
                        isLastPage = true
                    } else {
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
                    isLoading = false
                }
            }
        })
    }

    private fun loadMoreMovies() {
        currentPage++
        fetchMovies()
    }

    private fun showProgressBar() {
        runOnUiThread {
            progressBar.visibility = View.VISIBLE
        }
    }

    private fun hideProgressBar() {
        runOnUiThread {
            progressBar.visibility = View.GONE
        }
    }
}
