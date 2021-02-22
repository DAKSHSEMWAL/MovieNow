package com.varistor.assignment.model

import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import com.varistor.assignment.network.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class Movies : BaseObservable() {
    var status: String? = null
    private val breedsList: MutableList<Movie> = ArrayList()
    val movie = MutableLiveData<List<Movie?>>()
    @JvmField
    var showEmpty: ObservableInt? = null
    fun addBreed(bd: Movie) {
        breedsList.add(bd)
    }

    fun fetchList() {
        val callback: Callback<Movies?> = object : Callback<Movies?> {
            override fun onResponse(call: Call<Movies?>, response: Response<Movies?>) {
                val body = response.body()
                status = body!!.status
                movie.value = body.breedsList
            }

            override fun onFailure(call: Call<Movies?>, t: Throwable) {
                Log.e("Test", t.message, t)

            }
        }
        Api.api!!.breeds!!.enqueue(callback)
    }
}