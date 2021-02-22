package com.varistor.assignment.viewmodel

import android.view.View
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.varistor.assignment.R
import com.varistor.assignment.adapters.MovieListAdapter
import com.varistor.assignment.model.Movie
import com.varistor.assignment.model.Movies
import com.varistor.assignment.model.NetworkStatus

class MoviesViewModel : ViewModel() {
    private var movies: Movies? = null
    var adapter: MovieListAdapter? = null
        private set
    var selected: MutableLiveData<Movie?>? = null
    @JvmField
    var loading: ObservableInt? = null
    @JvmField
    var network: ObservableInt? = null
    @JvmField
    var showEmpty: ObservableInt? = null

    @JvmField
    var showRecyclerView: ObservableInt? = null

    lateinit var showString: ObservableField<String>

    fun init() {
        movies = Movies()
        selected = MutableLiveData()
        adapter = MovieListAdapter(R.layout.view_item_movie, this)
        loading = ObservableInt(View.GONE)
        showEmpty = ObservableInt(View.GONE)
        showRecyclerView = ObservableInt(View.GONE)
        showString = ObservableField("No Data Found")
        network = ObservableInt(View.GONE)
    }

    fun fetchList() {
        movies!!.fetchList()
    }

    val breeds: MutableLiveData<List<Movie?>>
        get() = movies!!.movie

    fun setMoviesInAdapter(breeds: List<Movie>?) {
        adapter!!.setDogBreeds(breeds)
        adapter!!.notifyDataSetChanged()
    }

    fun onItemClick(index: Int?) {
        val db = getMovieAt(index)
        selected!!.value = db
    }

    fun getMovieAt(index: Int?): Movie? {
        return if (movies!!.movie.value != null && index != null && movies!!.movie.value!!.size > index) {
            movies!!.movie.value!!.get(index)
        } else null
    }

}