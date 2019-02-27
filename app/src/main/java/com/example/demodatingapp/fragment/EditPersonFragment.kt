package com.example.demodatingapp.fragment

import android.Manifest
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.demodatingapp.R
import com.example.demodatingapp.databinding.FragmentEditPersonBinding
import com.example.demodatingapp.util.ContentEditingParent
import com.example.demodatingapp.util.ImageChooser
import com.example.demodatingapp.util.ImageLoader
import com.example.demodatingapp.util.RetryCallback
import com.example.demodatingapp.viewmodel.EditPersonViewModel
import com.example.demodatingapp.viewmodel.PersonFields
import com.example.demodatingapp.viewmodel.factory.PersonViewModelFactory
import com.example.demodatingapp.vo.Status
import java.io.File

class EditPersonFragment: ContentEditingParent() {

    private lateinit var binding: FragmentEditPersonBinding

    private lateinit var viewModel: EditPersonViewModel

    private lateinit var personFields: PersonFields

    private lateinit var imageChooser: ImageChooser

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_person, container, false)
        personFields = EditPersonFragmentArgs.fromBundle(arguments!!).person
        binding.personFields = personFields
        binding.retryCallback = object : RetryCallback {
            override fun retry() {
                viewModel.retry()
            }
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, PersonViewModelFactory(application()))
            .get(EditPersonViewModel::class.java)
        viewModel.fields = personFields
        viewModel.updatePerson.observe(this, Observer {
            binding.resource = it
            if (it.status == Status.SUCCESS) {
                findNavController().navigate(
                    EditPersonFragmentDirections.actionFragmentEditPersonToFragmentDetail(it.data!!.id)
                )
            }
        })
        binding.finishEditPersonButton.setOnClickListener {
            viewModel.fields = personFields
            if (viewModel.validate()) {
                viewModel.retry()
            }
        }
        binding.addImageButton.setOnClickListener {
            imageChooser = ImageChooser(this, arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            ))
            imageChooser.startChoosing()
        }
        binding.retryCallback = object : RetryCallback {
            override fun retry() {
                if (viewModel.validate()) {
                    viewModel.retry()
                }
            }
        }
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
                fileName.let {
                    ImageLoader.getInstance(requireContext())
                        .loadCircular(file.toUri(),
                            R.drawable.placeholder, R.drawable.error_image, binding.personAvatar)
                    personFields.bigPhotoName = file.name
                }
            }
        }
    }
}