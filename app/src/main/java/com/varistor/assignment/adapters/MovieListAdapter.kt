package com.varistor.assignment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.varistor.assignment.BR
import com.varistor.assignment.adapters.MovieListAdapter.GenericViewHolder
import com.varistor.assignment.model.Movie
import com.varistor.assignment.viewmodel.MoviesViewModel

class MovieListAdapter(@param:LayoutRes private val layoutId: Int, private val viewModel: MoviesViewModel) : RecyclerView.Adapter<GenericViewHolder>() {
    private var movies: List<Movie>? = null
    private fun getLayoutIdForPosition(position: Int): Int {
        return layoutId
    }

    override fun getItemCount(): Int {
        return if (movies == null) 0 else movies!!.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, viewType, parent, false)
        return GenericViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenericViewHolder, position: Int) {
        holder.bind(viewModel, position)
    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutIdForPosition(position)
    }

    fun setMovies(movies_: List<Movie>?) {
        this.movies = movies_
    }

    inner class GenericViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(viewModel: MoviesViewModel, position: Int?) {
            binding.setVariable(BR.viewModel, viewModel)
            binding.setVariable(BR.position, position)
            binding.setVariable(BR.movies, viewModel.getMovieAt(position))
            binding.executePendingBindings()
        }
    }
}