package com.example.demodatingapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.demodatingapp.R
import com.example.demodatingapp.databinding.FragmentAddPersonBinding
import com.example.demodatingapp.viewmodel.AddPersonViewModel

class AddPersonFragment: Fragment() {

    lateinit var binding: FragmentAddPersonBinding

    lateinit var viewModel: AddPersonViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_person, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AddPersonViewModel::class.java)
    }
}