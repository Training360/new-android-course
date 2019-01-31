package com.example.demodatingapp.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.example.demodatingapp.databinding.ViewPersonDetailsHeaderBinding
import com.example.demodatingapp.util.ImageLoader

class PersonDetailHeaderView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    val binding: com.example.demodatingapp.databinding.ViewPersonDetailsHeaderBinding

    init {
        val layoutInflater = LayoutInflater.from(context)
        binding = ViewPersonDetailsHeaderBinding.inflate(layoutInflater, this, true)
    }

    companion object {
        @BindingAdapter("starCount")
        @JvmStatic
        fun TextView.starEmoji(count: Int) {
            val starEmojiHex = 0x2B50
            val starEmojiString = String(Character.toChars(starEmojiHex))
            text = starEmojiString.repeat(count)
        }

        @BindingAdapter("roundedResource")
        @JvmStatic
        fun ImageView.setRoundedResource(imageName: String?) {
            imageName?.let {
                ImageLoader.loadCircular(it, this)
            }
        }
    }
}