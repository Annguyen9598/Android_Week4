package com.example.android_week4

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
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
import com.example.android_week4.RoomPersistence.AppDatabase
import com.example.android_week4.RoomPersistence.MovieDAO
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.khtn.androidcamp.DataCenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NowPlaying.ListenerFromNowPlaingFragment , TopRating.ListenerFromTopRatingFragment{

    var favoriteMoviesList = ArrayList<Movie>()
    lateinit var dao :MovieDAO

//    private lateinit var viewPager : ViewPager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRoomDatabase()
        init()
        getMovies()
    }
    private fun initRoomDatabase() {
        val db = AppDatabase.invoke(this)
        dao = db.movieDAO()
    }
    private fun getMovies() {
        val movies = dao.getAll() // get Students from ROOM database
        this.favoriteMoviesList.addAll(movies) // add to student list
    }
    private  fun init(){


//        val adapter         = MovieAdapter(this,converJsonToData(),listener)
//        val layoutmanager   = LinearLayoutManager(this)

//        rv.layoutManager    = layoutmanager
//        rv.adapter          = adapter

//        toolbar = supportActionBar!!

//        FavoriteMovies = ArrayList<Movie>()

        val preference = getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

        val bottomNavigation : BottomNavigationView = findViewById(R.id.navigationView)
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        supportActionBar?.hide()

        val tab : Int = preference.getInt("KEY-TAB",0)
        when(tab){
            0 ->{
                openFragment(NowPlaying())
            }
            1 ->{
                openFragment(TopRating())
            }
            2 ->{
                openFragment(MyFavorite())
            }
        }



//        viewPager = findViewById(R.id.viewPager)
//
//        val pageAdapter = ViewPagerAdapter(supportFragmentManager)
//        viewPager.adapter = pageAdapter

//        if (supportFragmentManager.backStackEntryCount == 0) {
//            viewPager.currentItem = 0
//            openFragment(NowPlaying())
//        }

//        viewPager?.addOnPageChangeListener(object  : ViewPager.OnPageChangeListener{
//            override fun onPageScrollStateChanged(state: Int) {
//
//            }
//
//            override fun onPageScrolled(
//                position: Int,
//                positionOffset: Float,
//                positionOffsetPixels: Int
//            ) {
//
//            }
//
//            override fun onPageSelected(position: Int) {
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
//            }
//
//        })

    }
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId){
            R.id.item_nowPlaying ->{
//                viewPager.currentItem = 0
                val preference = getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
                val prefEditor = preference.edit()
                prefEditor.putInt("KEY-TAB", 0)
                prefEditor.apply()
                openFragment(NowPlaying())
                return@OnNavigationItemSelectedListener true;
            }
            R.id.item_topRating ->{
//                viewPager.currentItem = 1
                val preference = getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
                val prefEditor = preference.edit()
                prefEditor.putInt("KEY-TAB", 1)
                prefEditor.apply()
                openFragment(TopRating())
                return@OnNavigationItemSelectedListener true;
            }
            R.id.item_myFavorite ->{
//                viewPager.currentItem = 2
                val preference = getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
                val prefEditor = preference.edit()
                prefEditor.putInt("KEY-TAB", 2)
                prefEditor.apply()
                openFragment(MyFavorite())
                return@OnNavigationItemSelectedListener true;
            }
        }
        false
    }
    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    public fun getFavoriteMovies() : ArrayList<Movie>{
        return favoriteMoviesList
    }

    override fun onFragmentListener(movie: Movie) {
        dao.insert(movie)
    }

}
