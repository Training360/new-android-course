package com.example.demodatingapp.view

import android.content.Context
import android.graphics.BitmapFactory
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.example.demodatingapp.R

class PersonDetailHeaderView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val mAvatarImageView: ImageView
    private val mPersonName: TextView
    private val mPersonJob: TextView
    private val mPersonRating: TextView
    private val mPersonAge: TextView

    init {
        LayoutInflater.from(context).inflate(R.layout.view_person_details_header, this, true)
        mAvatarImageView = findViewById(R.id.person_avatar)
        mPersonName = findViewById(R.id.person_visible_name)
        mPersonJob = findViewById(R.id.person_job)
        mPersonRating = findViewById(R.id.person_rating)
        mPersonAge = findViewById(R.id.person_age)

        setup()
    }

    private fun setup() {
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.user_avatar)
        val drawable = RoundedBitmapDrawableFactory.create(resources, bitmap)
        drawable.isCircular = true

        mAvatarImageView.setImageDrawable(drawable)
        mPersonName.text = "Gyula"
        mPersonJob.text = "Informatikus"
        mPersonRating.text = "****"
        mPersonAge.text = "27 éves"
    }
}