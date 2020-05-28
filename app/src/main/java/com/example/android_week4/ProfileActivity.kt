package com.example.android_week4

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        init()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    private fun init(){
        val toolbar1 = findViewById(R.id.toolbar1) as Toolbar?
        setSupportActionBar(toolbar1)

        val actionbar = supportActionBar
        actionbar?.title = "Movies"
        actionbar?.elevation = 4.0F
        actionbar?.setDisplayHomeAsUpEnabled(true)
        getAndDisplayData()
    }
    private fun getAndDisplayData() {
        val data = intent.extras?.getParcelable<Movie>("crrmovie")

        if (data != null) {
            val title = data.title
            val overview = data.overview
            val poster = data.getposter()
            val banner = data.getbanner()
            textviewtitlemovie.text = title
            textviewoverview.text   = overview
            tvName.text             = title
            tvdescription .text     = overview
            Glide.with(this)
                .load(banner)
                .centerCrop()
                .into(imageviewbackgroundmovie)
            Glide.with(this)
                .load(poster)
                .centerCrop()
                .into(imageviewposter)
        }
    }
}