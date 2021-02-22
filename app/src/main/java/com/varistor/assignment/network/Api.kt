package com.varistor.assignment.network

import com.google.gson.GsonBuilder
import com.varistor.assignment.model.Movies
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

object Api {
    var api: ApiInterface? = null
        get() {
            if (field == null) {
                val logging = HttpLoggingInterceptor()
                logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
                val client: OkHttpClient = OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build()
                val gson = GsonBuilder()
                    .registerTypeAdapter(
                        Movies::class.java,
                        JsonMoviessDeserializer()
                    )
                    .create()
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
                field = retrofit.create(ApiInterface::class.java)
            }
            return field
        }
        private set
    private const val BASE_URL = "https://www.test.varix.in"

    interface ApiInterface {
        @get:GET("/movie_list.php")
        val breeds: Call<Movies?>?
    }
}