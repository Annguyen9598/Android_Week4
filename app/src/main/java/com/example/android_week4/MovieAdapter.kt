package com.example.android_week4

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MovieAdapter(val ctx : Context, val movies : ArrayList<Movie>, val listener : MovieListener) : RecyclerView.Adapter<MovieAdapter.movieVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): movieVH {

        var view : View = LayoutInflater.from(ctx).inflate(R.layout.movie_item,parent,false)
        return movieVH(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: movieVH, position: Int) {
        val movie : Movie = movies[position]

        Glide.with(ctx)
            .load(movie.getposter())
            .centerCrop()
            .into(holder.ivPoster)
        holder.tvTitle.text         = movie.title
        holder.tvDescription.text   = movie.overview
        holder.itemView.setOnClickListener{
            listener?.onClick(position,movie)
        }
        holder.starIcon.setOnClickListener{
            listener?.addFavoriteMovie(position,movie)
        }
    }
    interface MovieListener{
        fun onClick(pos : Int, movie : Movie)
        fun addFavoriteMovie(pos : Int, movie : Movie)
    }
    class movieVH(itemView : View) : RecyclerView.ViewHolder(itemView){
        val ivPoster        = itemView.findViewById<ImageView>(R.id.imageView)
        val tvTitle         = itemView.findViewById<TextView>(R.id.tvTitle)
        val tvDescription   = itemView.findViewById<TextView>(R.id.tvOverview)
        val starIcon        = itemView.findViewById<ImageView>(R.id.imStarIcon)
    }
}