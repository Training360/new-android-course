package com.example.demodatingapp.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.demodatingapp.R
import com.example.demodatingapp.view.PersonDetailGalleryView
import com.example.demodatingapp.view.PersonDetailHeaderView
import com.example.demodatingapp.view.PersonDetailIntroductionView
import com.example.demodatingapp.viewmodel.PersonDetailViewModel

class MainActivity : AppCompatActivity() {

    lateinit var headerView: PersonDetailHeaderView

    lateinit var gallery: PersonDetailGalleryView

    lateinit var introductionView: PersonDetailIntroductionView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        headerView = findViewById(R.id.person_detail_header)
        gallery = findViewById(R.id.gallery)
        introductionView = findViewById(R.id.person_detail_introduction)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val model = ViewModelProviders.of(this).get(PersonDetailViewModel::class.java)
        model.getUser().observe(this, Observer {
            if (it != null) {
                gallery.bind(it)
                headerView.bind(it)
                introductionView.bind(it)
            }
        })
    }
}
