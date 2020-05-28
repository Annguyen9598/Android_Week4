package com.example.android_week4

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_now_playing.*

class MyFavorite : Fragment() {
    lateinit var btnList : ImageButton
    lateinit var btnGrid : ImageButton
    lateinit var FavoriteMovies : ArrayList<Movie>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FavoriteMovies = ArrayList<Movie>()

        val activity : MainActivity = activity as MainActivity
        FavoriteMovies = activity.getFavoriteMovies()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_favorite,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnList = view?.findViewById<ImageButton>(R.id.now_btn_list)
        btnGrid = view?.findViewById<ImageButton>(R.id.now_btn_grid)


        var layoutmanager : LinearLayoutManager = LinearLayoutManager(context)
        var adapter = context?.let { MovieAdapter(it,FavoriteMovies,listener) }

        rv.layoutManager = layoutmanager
        rv.adapter       = adapter

        btnList.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                var layoutmanager: LinearLayoutManager = LinearLayoutManager(context)
                var adapter = context?.let { MovieAdapter(it,FavoriteMovies,listener) }
                rv.layoutManager = layoutmanager
                rv.adapter       = adapter
            }
        })
        btnGrid.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                var layoutmanager: GridLayoutManager = GridLayoutManager(context, 2)
                var adapter = context?.let { GridMovieAdapter(it,FavoriteMovies,listener1) }
                rv.layoutManager = layoutmanager
                rv.adapter       = adapter
            }
        })
    }
    private var listener = object : MovieAdapter.MovieListener{
        override fun onClick(pos: Int, movie: Movie) {
            startProfileActivity(movie)
        }

        override fun addFavoriteMovie(pos: Int, movie: Movie) {

        }
    }
    private var listener1 = object : GridMovieAdapter.MovieListener{
        override fun onClick(pos: Int, movie: Movie) {
            startProfileActivity(movie)
        }

        override fun addFavoriteMovie(pos: Int, movie: Movie) {

        }
    }
    private fun startProfileActivity(movie: Movie) {
        val intent = Intent (activity,ProfileActivity::class.java)
        intent.putExtra("crrmovie",movie)
        activity?.startActivity(intent)
    }
}