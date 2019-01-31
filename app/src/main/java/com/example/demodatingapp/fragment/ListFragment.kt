package com.example.demodatingapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.demodatingapp.adapter.PersonAdapter
import com.example.demodatingapp.databinding.FragmentListBinding
import com.example.demodatingapp.viewmodel.PersonListViewModel
import com.example.demodatingapp.viewmodel.factory.PersonViewModelFactory

class ListFragment: Fragment() {

    lateinit var viewModel: PersonListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentListBinding.inflate(inflater, container, false)

        viewModel = ViewModelProviders.of(this, PersonViewModelFactory.INSTANCE).get(PersonListViewModel::class.java)
        val adapter = PersonAdapter()
        binding.personList.adapter = adapter
        viewModel.persons.observe(this, Observer {
            if (it != null) adapter.submitList(it.data)
        })

        return binding.root
    }
}