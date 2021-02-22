package com.varistor.assignment.model

import androidx.databinding.BaseObservable

class Movie : BaseObservable() {
    var name: String? = null
    var image: String? = null
    var genre: String? = null
    var url: String? = null
    var movie_year: String? = null
}