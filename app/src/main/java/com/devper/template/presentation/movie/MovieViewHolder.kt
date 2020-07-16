package com.devper.template.presentation.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.devper.template.R
import com.devper.template.domain.model.movie.Movie

class MovieViewHolder(val view: View, private val f: (movie: Movie) -> Unit) :
    RecyclerView.ViewHolder(view) {

    private val titleTextView: TextView = view.findViewById(R.id.titleTextView)
    private val popularityTextView: TextView = view.findViewById(R.id.popularityTextView)
    private val imageView: ImageView = view.findViewById(R.id.imageView)
    private lateinit var movie: Movie

    init {
        view.setOnClickListener {
            f.invoke(movie)
        }
    }

    fun bind(movie: Movie?) {
        movie?.let {
            this.movie = it
            popularityTextView.text = it.popularityDisplay
            titleTextView.text = it.title
            imageView.load(it.posterPath)
        }
    }

    companion object {
        fun create(parent: ViewGroup, f: (movie: Movie) -> Unit): MovieViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
            return MovieViewHolder(view, f)
        }
    }

}