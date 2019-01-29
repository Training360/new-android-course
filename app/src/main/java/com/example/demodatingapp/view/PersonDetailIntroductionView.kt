package com.example.demodatingapp.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.demodatingapp.R
import com.example.demodatingapp.model.PersonModel

class PersonDetailIntroductionView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private val mTitleEditText: TextView
    private val mBodyEditText: EditText

    init {
        LayoutInflater.from(context).inflate(R.layout.view_person_details_introduction, this, true)
        mTitleEditText = findViewById(R.id.introduction_title)
        mBodyEditText = findViewById(R.id.introduction_body)
    }

    fun bind(model: PersonModel) {
        mBodyEditText.setText(model.introduction)
    }
}