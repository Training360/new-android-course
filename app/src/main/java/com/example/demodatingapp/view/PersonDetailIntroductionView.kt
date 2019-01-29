package com.example.demodatingapp.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.demodatingapp.R

class PersonDetailIntroductionView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private val mTitleEditText: TextView
    private val mBodyEditText: EditText

    init {
        LayoutInflater.from(context).inflate(R.layout.view_person_details_introduction, this, true)
        mTitleEditText = findViewById(R.id.introduction_title)
        mBodyEditText = findViewById(R.id.introduction_body)

        setup()
    }

    private fun setup() {
        mTitleEditText.text = "Első randis tervekről pár szóban"
        val string = "Arra gondoltam, hogy valami jó olaszos kaja ki tudja hozni az emberek valódi énjét.\n\nSzóval... bedobhatnánk egy pizzát valamikor, szerintem az egészen király lenne.\n\nCiao!"
        mBodyEditText.setText(string)
    }
}