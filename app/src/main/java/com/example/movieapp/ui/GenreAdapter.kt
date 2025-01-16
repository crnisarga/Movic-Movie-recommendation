package com.example.movieapp.ui

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.model.data.Genre
import com.example.movieapp.model.data.Movie

class GenreAdapter(private var movieListMap: Map<Int, List<Movie>>) : ListAdapter<Genre, GenreAdapter.GenreViewHolder>(GenreDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_genre, parent, false)
        Log.d("ncr","the cursor entered here ")
        return GenreViewHolder(view)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        Log.d("ncr","the value received in the adapter ${movieListMap}")
        val genre = getItem(position)

        holder.genre_name.text = genre.name
        holder.bind(genre)

        // Fetch the list of movies for this genre
        val movieList = movieListMap[genre.id] ?: emptyList()

        Log.d("ncr","the movie list is ${movieList}")

        // Set up the MovieAdapter for the current genre
        holder.bindMovies(movieList)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, MainActivity::class.java)
            intent.putExtra("genre_id", genre.id)
            holder.itemView.context.startActivity(intent)
        }
    }

    // Method to update the movie list map
    fun updateMovieListMap(newMovieListMap: Map<Int, List<Movie>>) {
        movieListMap = newMovieListMap
        notifyDataSetChanged()  // Notify adapter to refresh the list
    }

    class GenreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val genre_name: TextView = itemView.findViewById(R.id.genres_name)
        private val moviesRecyclerView: RecyclerView = itemView.findViewById(R.id.rvMoviess)

        fun bind(genre: Genre) {
            // Additional binding logic for the genre if necessary
        }

        // Bind the list of movies to the nested RecyclerView
        fun bindMovies(movieList: List<Movie>) {
            val movieAdapter = MovieAdapter()
            moviesRecyclerView.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            moviesRecyclerView.adapter = movieAdapter

            // Pass the movie list to the MovieAdapter
            movieAdapter.submitList(movieList)
        }
    }

    class GenreDiffCallback : DiffUtil.ItemCallback<Genre>() {
        override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean {
            return oldItem == newItem
        }
    }
}
