package com.example.movieapp.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.model.data.Genre

class GenreAdapter(private val genreList: List<Genre>) :  RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_genre, parent, false)
        return GenreViewHolder(view)
    }

    override fun getItemCount(): Int {
        return genreList.size
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val genre = genreList[position]

        holder.genre_name.text = genre.name
        Log.d("ncr","the genre name is ${genre.name}")
    }

    class GenreViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        val genre_name: TextView = itemView.findViewById(R.id.genre_name)
    }
}