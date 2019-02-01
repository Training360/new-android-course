package com.example.demodatingapp.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import com.example.demodatingapp.R
import com.example.demodatingapp.adapter.PersonAdapter
import com.example.demodatingapp.databinding.FragmentListBinding
import com.example.demodatingapp.util.RetryCallback
import com.example.demodatingapp.viewmodel.PersonListViewModel
import com.example.demodatingapp.viewmodel.factory.PersonViewModelFactory

class ListFragment: Fragment() {

    lateinit var viewModel: PersonListViewModel

    lateinit var mBinding: FragmentListBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentListBinding.inflate(inflater, container, false)

        viewModel = ViewModelProviders.of(this, PersonViewModelFactory(application()))
            .get(PersonListViewModel::class.java)

        val adapter = PersonAdapter()

        mBinding.personList.adapter = adapter
        viewModel.persons.observe(this, Observer {
            mBinding.listResource = it
            if (it != null) adapter.submitList(it.data)
        })

        setHasOptionsMenu(true)

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.retryCallback = object : RetryCallback {
            override fun retry() {
                viewModel.retry()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController()) || super.onOptionsItemSelected(item)
    }
}