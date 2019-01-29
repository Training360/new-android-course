package com.example.demodatingapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import com.example.demodatingapp.R
import com.example.demodatingapp.model.PersonModel
import com.example.demodatingapp.view.PersonDetailHeaderView
import com.example.demodatingapp.view.PersonDetailIntroductionView

class MainActivity : AppCompatActivity() {

    lateinit var headerView: PersonDetailHeaderView

    lateinit var imageView: ImageView

    lateinit var introductionView: PersonDetailIntroductionView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val model = PersonModel.georgeClooney()

        headerView = findViewById(R.id.person_detail_header)
        headerView.bind(model)

        imageView = findViewById(R.id.person_image)
        imageView.setImageResource(model.image)

        introductionView = findViewById(R.id.person_detail_introduction)
        introductionView.bind(model)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
    }
}
