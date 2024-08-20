package com.ziadahmed.logintask

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class MoviePageActivity : AppCompatActivity() {

    private lateinit var titleTextView: TextView
    private lateinit var yearTextView: TextView
    private lateinit var ratingTextView: TextView
    private lateinit var descriptionIntroTextView: TextView
    private lateinit var descriptionFullTextView: TextView
    private lateinit var languageTextView: TextView
    private lateinit var dateUploadedTextView: TextView
    private lateinit var mpaRatingTextView: TextView
    private lateinit var coverImageView: ImageView

    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_page)

        titleTextView = findViewById(R.id.movie_title_d)
        yearTextView = findViewById(R.id.movie_year_d)
        ratingTextView = findViewById(R.id.movie_rating_d)
        descriptionIntroTextView = findViewById(R.id.movie_description_intro)
        descriptionFullTextView = findViewById(R.id.movie_description_full)
        languageTextView = findViewById(R.id.movie_language)
        mpaRatingTextView = findViewById(R.id.movie_mpa_rating)
        dateUploadedTextView = findViewById(R.id.dateUploaded)
        coverImageView = findViewById(R.id.movie_image)

        val movieId = intent.getIntExtra("movie_id", -1)
        if (movieId != -1) {
            fetchMovieDetails(movieId)
        }
    }

    private fun fetchMovieDetails(movieId: Int) {
        val url = "https://ww4.yts.nz//api/v2/movie_details.json?movie_id=$movieId" // Replace with your actual URL

        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                runOnUiThread {}
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    val jsonResponse = JSONObject(response.body!!.string())
                    print(jsonResponse.toString())
                    val movieJson = jsonResponse.getJSONObject("data").getJSONObject("movie")

                    val movie = Specific_move_model(
                        id = movieJson.getLong("id"),
                        url = movieJson.getString("url"),
                        imdbCode = movieJson.getString("imdb_code"),
                        title = movieJson.getString("title"),
                        titleEnglish = movieJson.getString("title_english"),
                        titleLong = movieJson.getString("title_long"),
                        slug = movieJson.getString("slug"),
                        year = movieJson.getLong("year"),
                        rating = movieJson.getDouble("rating"),
                        runtime = movieJson.getLong("runtime"),
                        genres = movieJson.getJSONArray("genres").let {
                            List(it.length()) { j -> it.getString(j) }
                        },
                        likeCount = movieJson.getLong("like_count"),
                        descriptionIntro = movieJson.getString("description_intro"),
                        descriptionFull = movieJson.getString("description_full"),
                        ytTrailerCode = movieJson.getString("yt_trailer_code"),
                        language = movieJson.getString("language"),
                        mpaRating = movieJson.getString("mpa_rating"),
                        backgroundImage = movieJson.getString("background_image"),
                        backgroundImageOriginal = movieJson.getString("background_image_original"),
                        smallCoverImage = movieJson.getString("small_cover_image"),
                        mediumCoverImage = movieJson.getString("medium_cover_image"),
                        largeCoverImage = movieJson.getString("large_cover_image"),
                        dateUploaded = movieJson.getString("date_uploaded"),
                        dateUploadedUnix = movieJson.getLong("date_uploaded_unix")
                    )
                    print(movie.toString())


                    runOnUiThread {
                        titleTextView.text = movie.title
                        yearTextView.text = if (movie.year != null) movie.year.toString() else "Unknown"
                        ratingTextView.text = if (movie.rating != null) "${movie.rating} ⭐" else "N/A"
                        descriptionIntroTextView.text = movie.descriptionIntro
                        descriptionFullTextView.text = movie.descriptionFull
                        languageTextView.text = movie.language
                        mpaRatingTextView.text = movie.mpaRating
                        dateUploadedTextView.text = movie.dateUploaded

                        val imageUrl = "https://ww4.yts.nz${movie.backgroundImage}"
                        Picasso.get().load(imageUrl).into(coverImageView)


                        Log.d("MoviePageActivity", "Description Full: ${movie.descriptionIntro.chars()
                        }")


                    }
                }
            }
        })
    }
}
