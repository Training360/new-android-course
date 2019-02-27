package com.example.demodatingapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.demodatingapp.R
import com.example.demodatingapp.databinding.FragmentEditPersonBinding

class EditPersonFragment: Fragment() {

    private lateinit var binding: FragmentEditPersonBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_person, container, false)
        val fields = EditPersonFragmentArgs.fromBundle(arguments!!).person
        binding.personFields = fields
        return binding.root
    }
}