package com.example.android_week4

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.example.android_week4.Adapter.ViewPagerAdapter
import com.example.android_week4.Fragment.MyFavorite
import com.example.android_week4.Fragment.NowPlaying
import com.example.android_week4.Fragment.TopRating
import com.example.android_week4.Movie
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.khtn.androidcamp.DataCenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NowPlaying.ListenerFromNowPlaingFragment , TopRating.ListenerFromTopRatingFragment{

//    lateinit var toolbar : ActionBar
    var favoriteMoviesList = ArrayList<Movie>()
    private lateinit var viewPager : ViewPager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }
//    private val listener = object : MovieAdapter.MovieListener{
//        override fun onClick(pos: Int, movie: Movie) {
//            startProfileActivity(movie)
//        }
//
//    }
//    private val listener1 = object : GridMovieAdapter.MovieListener{
//        override fun onClick(pos: Int, movie: Movie) {
//            startProfileActivity(movie)
//        }
//
//    }
    private  fun init(){
//        val toolbar = findViewById(R.id.toolbar2) as Toolbar?
//        setSupportActionBar(toolbar)
//
//        val actionbar = supportActionBar
//        actionbar?.title = "Movies"
//        actionbar?.elevation = 4.0F
//        actionbar?.setDisplayHomeAsUpEnabled(true)


//        val adapter         = MovieAdapter(this,converJsonToData(),listener)
//        val layoutmanager   = LinearLayoutManager(this)

//        rv.layoutManager    = layoutmanager
//        rv.adapter          = adapter

//        toolbar = supportActionBar!!

//        FavoriteMovies = ArrayList<Movie>()

        val bottomNavigation : BottomNavigationView = findViewById(R.id.navigationView)

        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        supportActionBar?.hide()
        viewPager = findViewById(R.id.viewPager)

        val pageAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPager.adapter = pageAdapter

//        if (supportFragmentManager.backStackEntryCount == 0) {
//            viewPager.currentItem = 0
//            openFragment(NowPlaying())
//        }
        viewPager?.addOnPageChangeListener(object  : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
//                if(position == 0){
//                    Toast.makeText(this@MainActivity,"Fragment1",
//                        Toast.LENGTH_LONG).show();
//                }else if(position == 1){
//                    Toast.makeText(this@MainActivity,"Fragment2",
//                        Toast.LENGTH_LONG).show();
//                }else{
//                    Toast.makeText(this@MainActivity,"Fragment3",
//                        Toast.LENGTH_LONG).show();
//                }
//                bottomNavigation.menu.getItem(position).setChecked(true)
            }

        })

    }
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId){
            R.id.item_nowPlaying ->{
                viewPager.currentItem = 0
                openFragment(NowPlaying())
                return@OnNavigationItemSelectedListener true;
            }
            R.id.item_topRating ->{
                viewPager.currentItem = 1
                openFragment(TopRating())
                return@OnNavigationItemSelectedListener true;
            }
            R.id.item_myFavorite ->{
                viewPager.currentItem = 2
                openFragment(MyFavorite())
                return@OnNavigationItemSelectedListener true;
            }
        }
        false
    }
    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
//        transaction.addToBackStack(null)
        transaction.commit()
    }

    public fun getFavoriteMovies() : ArrayList<Movie>{
        return favoriteMoviesList
    }

    override fun onFragmentListener(movie: Movie) {
        favoriteMoviesList.add(movie)
    }

//    private fun startProfileActivity(movie: Movie){
//        val intent = Intent(this@MainActivity, ProfileActivity::class.java)
//        intent.putExtra("MOVIE_TITLE_KEY", movie.title)
//        intent.putExtra("IDOL_POSTER_KEY", movie.getposter())
//        intent.putExtra("IDOL_OVERVIEW_KEY", movie.overview)
//        intent.putExtra("IDOL_BANNER_KEY", movie.getbanner())
//        startActivity(intent)
//    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu to use in the action bar
//        val inflater = menuInflater
//        inflater.inflate(R.menu.toolbar_menu, menu)
//        return super.onCreateOptionsMenu(menu)
//    }
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle presses on the action bar menu items
//        when (item.itemId) {
//            R.id.list_view -> {
////                text_view.text = "List View"
//                val adapter         = MovieAdapter(this,converJsonToData(),listener)
//                val layoutmanager   = LinearLayoutManager(this)
//
////                rv.layoutManager    = layoutmanager
////                rv.adapter          = adapter
//                return true
//            }
//            R.id.grid_view -> {
////                text_view.text = "Grid View"
//                val adapter         = GridMovieAdapter(this,converJsonToData(),listener1)
//                val layoutmanager = GridLayoutManager(this,2)
////                rv.layoutManager    = layoutmanager
////                rv.adapter          = adapter
//                return true
//            }
//
//        }
//        return super.onOptionsItemSelected(item)
//    }


}
