package com.example.demodatingapp.fragment

import androidx.fragment.app.Fragment
import com.example.demodatingapp.JingleApplication

fun Fragment.application(): JingleApplication {
    return activity!!.application as JingleApplication
}