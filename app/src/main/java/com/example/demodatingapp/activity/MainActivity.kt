package com.example.demodatingapp.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.demodatingapp.R
import com.example.demodatingapp.adapter.GalleryAdapter
import com.example.demodatingapp.viewmodel.PersonDetailViewModel

class MainActivity : AppCompatActivity() {

    lateinit var mBinding: com.example.demodatingapp.databinding.ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(mBinding.toolbar)

        val model = ViewModelProviders.of(this).get(PersonDetailViewModel::class.java)
        model.getUser().observe(this, Observer {
            if (it != null) {
                mBinding.gallery.mViewPager.adapter = GalleryAdapter(it.galleryImages, this)
                mBinding.personDetailHeader.binding.person = it
                mBinding.personDetailIntroduction.binding.person = it
            }
        })
    }
}
