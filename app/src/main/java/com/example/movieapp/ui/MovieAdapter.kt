package com.example.movieapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.model.data.Movie

class MovieAdapter(private val movieList: List<Movie>) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieList[position]

        holder.tvMovieTitle.text = movie.title
        holder.tvMovieRating.text = "Rating: ${movie.vote_average}"

        val imageUrl = "https://image.tmdb.org/t/p/w500${movie.poster_path}"
        Glide.with(holder.itemView.context)
            .load(imageUrl)
            .placeholder(R.drawable.ic_launcher_background)  // Placeholder while loading
            .error(R.drawable.ic_launcher_foreground)  // Error image if loading fails
            .into(holder.imgMoviePoster)
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvMovieTitle: TextView = itemView.findViewById(R.id.tvMovieTitle)
        val tvMovieRating: TextView = itemView.findViewById(R.id.tvMovieRating)
        val imgMoviePoster: ImageView = itemView.findViewById(R.id.imgMoviePoster)
    }

}