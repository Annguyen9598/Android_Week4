package com.example.android_week4.Fragment

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_week4.*
import com.example.android_week4.Adapter.GridMovieAdapter
import com.example.android_week4.Adapter.MovieAdapter
import com.example.android_week4.Data.data
import com.example.android_week4.RoomPersistence.AppDatabase
import com.example.android_week4.RoomPersistence.MovieDAO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.khtn.androidcamp.DataCenter_1
import kotlinx.android.synthetic.main.fragment_now_playing.*

class NowPlaying : Fragment() {
    lateinit var btnList : ImageButton
    lateinit var btnGrid : ImageButton
    lateinit var mListener : ListenerFromNowPlaingFragment
    var FavoriteMovies = ArrayList<Movie>()

    private lateinit var db: AppDatabase
    lateinit var dao : MovieDAO
    private val TAG = NowPlaying::class.java.simpleName
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var movieGridAdapter: GridMovieAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activity : MainActivity = activity as MainActivity

        db = AppDatabase.invoke(activity)
        dao = db.movieDAO()
        val movies = dao.getAll()
        this.FavoriteMovies.addAll(movies)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_now_playing,container,false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnList = view?.findViewById<ImageButton>(R.id.now_btn_list)
        btnGrid = view?.findViewById<ImageButton>(R.id.now_btn_grid)

        var layoutmanager :LinearLayoutManager =LinearLayoutManager(context)
        movieAdapter = context?.let {
            MovieAdapter(it, converJsonToData(), listener)
        }!!
        movieGridAdapter = context?.let {
            GridMovieAdapter(it, converJsonToData(), listener1)
        }!!

        rv.layoutManager = layoutmanager
        rv.adapter       = movieAdapter

        btnList.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                var layoutmanager: LinearLayoutManager = LinearLayoutManager(context)
                rv.layoutManager = layoutmanager
                rv.adapter       = movieAdapter
            }
        })
        btnGrid.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                var layoutmanager: GridLayoutManager = GridLayoutManager(context, 2)
                rv.layoutManager = layoutmanager
                rv.adapter       = movieGridAdapter
            }
        })
    }

    private var listener = object :
        MovieAdapter.MovieListener {
        override fun onClick(pos: Int, movie: Movie) {
            startProfileActivity(movie)
        }

        override fun addFavoriteMovie(pos: Int, movie: Movie) {
            val builder = AlertDialog.Builder(activity)
                .setTitle("Alert")
                .setMessage("Ban muon them phim vao danh sach yeu thich?")
                .setPositiveButton("YES"){ _, _ ->
                    if(FavoriteMovies.contains(movie)){
                        Toast.makeText(context,"Phim da co trong danh sach ", Toast.LENGTH_SHORT).show()
                    }else{
                        addFvMovie(movie)
                        Toast.makeText(context,"Them phim vao danh sach thanh cong ", Toast.LENGTH_SHORT).show()
                    }
                }.setNegativeButton("NO"){dialog,_->dialog.dismiss() }
            val dialog = builder.create()
                dialog.show()
        }

        override fun removeFavoriteMovie(pos: Int, movie: Movie) {

        }
    }
    private var listener1 = object :
        GridMovieAdapter.MovieListener {
        override fun onClick(pos: Int, movie: Movie) {
            startProfileActivity(movie)
        }

        override fun addFavoriteMovie(pos: Int, movie: Movie) {
            val builder = AlertDialog.Builder(activity)
                .setTitle("Alert")
                .setMessage("Ban muon them phim vao danh sach yeu thich?")
                .setPositiveButton("YES"){ _, _ ->
                    if(FavoriteMovies.contains(movie)){
                        Toast.makeText(context,"Phim da co trong danh sach ", Toast.LENGTH_SHORT).show()
                    }else{
                        addFvMovie(movie)
                        Toast.makeText(context,"Them phim vao danh sach thanh cong ", Toast.LENGTH_SHORT).show()
                    }
                }.setNegativeButton("NO"){dialog,_->dialog.dismiss() }
            val dialog = builder.create()
            dialog.show()
        }

        override fun removeFavoriteMovie(pos: Int, movie: Movie) {

        }
    }

    private fun addFvMovie(movie: Movie){
        mListener.onFragmentListener(movie)
    }
    interface ListenerFromNowPlaingFragment{
        fun onFragmentListener(movie : Movie)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is ListenerFromNowPlaingFragment){
            mListener = context
        }
    }
    private fun startProfileActivity(movie: Movie) {
        val intent = Intent (activity,
            ProfileActivity::class.java)
        intent.putExtra("crrmovie",movie)
        activity?.startActivity(intent)
    }
    private fun converJsonToData() :ArrayList<Movie>{
        val data = Gson().fromJson(DataCenter_1.getNowPlayingMovieJson(),
            data::class.java)
        val result = data.results

        val arrayTutorialType =object: TypeToken<ArrayList<Movie>>() {}.type;
        var movies :ArrayList<Movie> = Gson().fromJson(Gson().toJson(result),arrayTutorialType)
        return movies
    }
}