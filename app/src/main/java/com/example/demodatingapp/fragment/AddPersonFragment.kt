package com.example.demodatingapp.fragment

import android.Manifest
import android.content.Intent
import android.graphics.BitmapFactory
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.demodatingapp.R
import com.example.demodatingapp.databinding.FragmentAddPersonBinding
import com.example.demodatingapp.util.*
import com.example.demodatingapp.viewmodel.AddPersonViewModel
import com.example.demodatingapp.viewmodel.factory.PersonViewModelFactory
import com.example.demodatingapp.vo.Place
import com.example.demodatingapp.vo.Status
import java.io.File

class AddPersonFragment: ContentEditingParent() {

    lateinit var binding: FragmentAddPersonBinding

    lateinit var viewModel: AddPersonViewModel

    lateinit var imageChooser: ImageChooserInterface

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_person, container, false)
        binding.addImageButton.setOnClickListener {
            imageChooser.startChoosing()
        }
        binding.finishAddPersonButton.setOnClickListener {
            if (viewModel.validate()) {
                viewModel.personFields.location = LocationLiveData(context!!).lastLocation().place
                viewModel.retry()
            }
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, PersonViewModelFactory(application()))
            .get(AddPersonViewModel::class.java)
        viewModel.addPerson.observe(this, Observer {
            binding.resource = it
            if (it.status == Status.SUCCESS) {
                findNavController().popBackStack()
            }
        })
        binding.personFields = viewModel.personFields
        imageChooser = ImageChooser(this, arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        imageChooser.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        imageChooser.onRequestPermissionsResult(requestCode, permissions, grantResults)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onImageSelected(file: File) {
        val bitmap = BitmapFactory.decodeFile(file.absolutePath)
        binding.addImageButton.imageTintList = null
        binding.addImageButton.scaleType = ImageView.ScaleType.CENTER_CROP
        binding.addImageButton.setImageBitmap(bitmap)
        viewModel.upload(file) {
            it?.let { fileName ->
                viewModel.personFields.bigPhotoName = fileName
                ImageLoader.getInstance(requireContext())
                    .loadCircular(file.name, R.drawable.placeholder, R.drawable.error_image, binding.personAvatar)
            }
        }
    }
}

private val Location.place: Place?
    get() {
        return Place(latitude, longitude)
    }