package com.example.demodatingapp.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.demodatingapp.databinding.ViewPersonDetailsHeaderBinding

class PersonDetailHeaderView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    val binding: com.example.demodatingapp.databinding.ViewPersonDetailsHeaderBinding

    init {
        val layoutInflater = LayoutInflater.from(context)
        binding = ViewPersonDetailsHeaderBinding.inflate(layoutInflater, this, true)
    }
}