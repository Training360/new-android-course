package com.example.demodatingapp.util

import com.example.demodatingapp.network.api.JingleApi
import com.example.demodatingapp.threading.JingleExecutors
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.File
import java.io.IOException

object ImageUploader {
    fun upload(file: File, callback: (String?) -> Unit) {
        JingleExecutors.INSTANCE.networkIO.execute {
            JingleApi.uploadImage(file).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    JingleExecutors.INSTANCE.mainThread.execute {
                        callback(null)
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    JingleExecutors.INSTANCE.mainThread.execute {
                        if (response.isSuccessful) {
                            val fileName = file.name
                            callback(fileName)
                        }
                    }
                }
            })
        }
    }
}