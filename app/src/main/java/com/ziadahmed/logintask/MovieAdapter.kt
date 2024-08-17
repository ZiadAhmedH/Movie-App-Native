import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.ziadahmed.logintask.MoviePageActivity
import com.ziadahmed.logintask.R

class MoviesAdapter(private val movies: List<MovieModel>) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.movie_title)
        val yearTextView: TextView = itemView.findViewById(R.id.movie_year)
        val ratingTextView: TextView = itemView.findViewById(R.id.movie_rating)
        val coverImageView: ImageView = itemView.findViewById(R.id.movie_cover)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_ui, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.titleTextView.text = movie.title
        holder.yearTextView.text = movie.year.toString()
        holder.ratingTextView.text = movie.rating.toString() + " ⭐ "
        Picasso.get().load(movie.medium_cover_image).into(holder.coverImageView)

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, MoviePageActivity::class.java)
            intent.putExtra("movie_id", movie.id) // Pass only the movie ID
            context.startActivity(intent)
        }

    }

    override fun getItemCount() = movies.size
}
