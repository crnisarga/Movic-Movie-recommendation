package com.example.movieapp.ui

import android.content.Intent
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

class GenreAdapter :  ListAdapter<Genre, GenreAdapter.GenreViewHolder>(GenreDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_genre, parent, false)
        return GenreViewHolder(view)
    }


    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val genre = getItem(position)

        holder.genre_name.text = genre.name
        holder.bind(genre)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, MainActivity::class.java)
            intent.putExtra("genre_id",genre.id)
            holder.itemView.context.startActivity(intent)
        }
    }

    class GenreViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        val genre_name: TextView = itemView.findViewById(R.id.genres_name)

        private val moviesRecyclerView: RecyclerView = itemView.findViewById(R.id.rvMovies)

        fun bind(genre: Genre) {
            // Set up the nested RecyclerView for movies
            val movieAdapter = MovieAdapter()
            moviesRecyclerView.layoutManager = LinearLayoutManager(itemView.context)
            moviesRecyclerView.adapter = movieAdapter
        }
    }

    class GenreDiffCallback : DiffUtil.ItemCallback<Genre>() {
        override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean {
            return oldItem.id == newItem.id // Ensure genre IDs match
        }

        override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean {
            return oldItem == newItem // Compare the entire contents of the items
        }
    }
}



