package com.example.demodatingapp.util

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import androidx.fragment.app.Fragment
import com.example.demodatingapp.R
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

abstract class ContentEditingParent: Fragment() {
    abstract fun onImageSelected(file: File)
}

interface ImageChooserInterface {
    fun onRequestPermissionsResult(requestCode: Int,
                                   permissions: Array<out String>,
                                   grantResults: IntArray)
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    fun startChoosing()
}

class ImageChooser(private val fragment: ContentEditingParent,
                   private val requiredPermissions: Array<String>): ImageChooserInterface {

    companion object {
        const val REQUEST_CODE_PICK_IMAGE = 123
        const val REQUEST_CODE_NEEDED_PERMISSIONS = 11
    }

    private lateinit var output: String

    override fun startChoosing() {
        if (PermissionHelper.hasPermissions(fragment.requireContext(), requiredPermissions)) {
            pickImageFromGallery()
        } else {
            fragment.requestPermissions(requiredPermissions, REQUEST_CODE_NEEDED_PERMISSIONS)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<out String>,
                                            grantResults: IntArray) {
        val context = fragment.requireContext()
        if (requestCode == REQUEST_CODE_NEEDED_PERMISSIONS) {
            if (PermissionHelper.hasPermissions(context, requiredPermissions)) {
                pickImageFromGallery()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val isOk = resultCode == Activity.RESULT_OK
        if (requestCode == REQUEST_CODE_PICK_IMAGE && isOk && data != null) {
            data.data?.also {
                val context = fragment.requireContext()
                val bitmap = BitmapFactory.decodeStream(context.contentResolver.openInputStream(it))
                val path = saveBitmap(bitmap)
                fragment.onImageSelected(File(path))
            }
        }
    }

    private fun saveBitmap(bitmap: Bitmap): String? {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes)
        val file = createImageFile()
        file.createNewFile()
        val fo = FileOutputStream(file)
        fo.write(bytes.toByteArray())
        fo.close()
        return file.absolutePath
    }

    private fun pickImageFromGallery() {
        val title = fragment.getString(R.string.image_chooser_title)
        Intent().also {
            it.type = "image/*"
            it.action = Intent.ACTION_GET_CONTENT
            fragment.startActivityForResult(
                Intent.createChooser(it, title),
                REQUEST_CODE_PICK_IMAGE
            )
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = fragment.requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            output = absolutePath
        }
    }
}
