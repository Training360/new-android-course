package com.example.demodatingapp.network.api

import androidx.lifecycle.LiveData
import com.example.demodatingapp.BuildConfig
import com.example.demodatingapp.network.adapter.LiveDataCallAdapterFactory
import com.example.demodatingapp.network.api.response.ApiResponse
import com.example.demodatingapp.vo.CreatePersonModel
import com.example.demodatingapp.vo.Person
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.io.File

interface JingleApi {
    companion object {

        fun uploadImage(file: File): okhttp3.Call {
            val requestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(FILE_NAME_KEY, file.name, RequestBody.create(MEDIA_TYPE_PNG, file))
                .build()
            val request = Request.Builder()
                .url("$BASE_URL/upload")
                .post(requestBody)
                .build()
            return OK_HTTP_CLIENT.newCall(request)
        }

        private const val BASE_URL = BuildConfig.BASE_URL
        private val MEDIA_TYPE_PNG = MediaType.parse("image/png")
        private const val FILE_NAME_KEY = "image"

        private val LOGGER_INTERCEPTOR: HttpLoggingInterceptor by lazy {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            interceptor
        }
        private val OK_HTTP_CLIENT: OkHttpClient by lazy {
            val client = OkHttpClient.Builder()
            if (BuildConfig.DEBUG) {
                client.addInterceptor(LOGGER_INTERCEPTOR)
            }
            client.build()
        }

        val INSTANCE: JingleApi by lazy {
            val api = Retrofit.Builder()
                .client(OK_HTTP_CLIENT)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
                .create(JingleApi::class.java)
            api
        }
    }

    @GET("/persons")
    fun getPersons(): LiveData<ApiResponse<List<Person>>>

    @GET("/persons/{id}")
    fun getPerson(@Path("id") id: Int): LiveData<ApiResponse<Person>>

    @POST("persons")
    fun addPerson(@Body createPersonModel: CreatePersonModel): LiveData<ApiResponse<List<Person>>>

    @POST("persons/{id}")
    fun updatePerson(@Path("id") id: Int, @Body person: Person): LiveData<ApiResponse<Person>>
}