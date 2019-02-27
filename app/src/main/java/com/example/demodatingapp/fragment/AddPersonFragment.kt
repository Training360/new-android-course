package com.example.demodatingapp.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.demodatingapp.R
import com.example.demodatingapp.databinding.FragmentAddPersonBinding
import com.example.demodatingapp.util.ImagePicker
import com.example.demodatingapp.viewmodel.AddPersonViewModel

class AddPersonFragment: Fragment() {

    companion object {
        const val REQUEST_CODE_PICK_IMAGE = 123
    }

    lateinit var binding: FragmentAddPersonBinding

    lateinit var viewModel: AddPersonViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_person, container, false)

        binding.addImageButton.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Válassz képet"), REQUEST_CODE_PICK_IMAGE)
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AddPersonViewModel::class.java)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            val bitmap = ImagePicker.getImageFromResult(context!!, resultCode, data)
            binding.addImageButton.imageTintList = null
            binding.addImageButton.scaleType = ImageView.ScaleType.CENTER_CROP
            binding.addImageButton.setImageBitmap(bitmap)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}