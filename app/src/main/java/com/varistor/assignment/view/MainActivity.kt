package com.varistor.assignment.view

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.varistor.assignment.R
import com.varistor.assignment.databinding.ActivityMainBinding
import com.varistor.assignment.model.Movie
import com.varistor.assignment.receiver.ConnectivityDetection
import com.varistor.assignment.utils.Common
import com.varistor.assignment.viewmodel.MoviesViewModel


class MainActivity : AppCompatActivity(), ConnectivityDetection.ConnectivityReceiverListener {

    private var viewModel: MoviesViewModel? = null
    private lateinit var context: Context
    private lateinit var activityBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(activityBinding.root)
        context = this
        Common.setToolbarWithBackAndTitle(context, "Movies", false, 0)
        registerReceiver(
            ConnectivityDetection(),
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
        setupBindings(savedInstanceState)
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showNetworkMessage(isConnected)
    }

    override fun onResume() {
        super.onResume()
        ConnectivityDetection.connectivityReceiverListener = this
    }

    private fun showNetworkMessage(isConnected: Boolean) {
        if (!isConnected) {
            activityBinding.networkStatus.text = "You are offline"
            activityBinding.networkStatus.setTextColor(Color.RED)
            viewModel!!.showEmpty!!.set(View.VISIBLE)
            viewModel!!.showRecyclerView!!.set(View.GONE)
            viewModel!!.showString.set("You are currently offline")
            viewModel!!.loading!!.set(View.GONE)
        } else {
            activityBinding.networkStatus.text = "Back Online"
            viewModel!!.showEmpty!!.set(View.GONE)
            activityBinding.networkStatus.setTextColor(Color.GREEN)
            viewModel!!.showRecyclerView!!.set(View.VISIBLE)
            val mRunnable = Runnable { activityBinding.networkStatus.visibility = View.GONE }
            Handler(Looper.getMainLooper()).postDelayed(mRunnable, 10000)
            viewModel!!.loading!!.set(View.VISIBLE)
            viewModel!!.fetchList()
        }
    }


    private fun setupBindings(savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this).get(MoviesViewModel::class.java)
        if (savedInstanceState == null) {
            viewModel!!.init()
        }
        activityBinding.model = viewModel
        setupListUpdate()
    }

    private fun setupListUpdate() {
        viewModel!!.loading!!.set(View.VISIBLE)
        viewModel!!.fetchList()
        viewModel!!.breeds.observe(this, {
            viewModel!!.loading!!.set(View.GONE)
            if (it.isEmpty()) {
                viewModel!!.showEmpty!!.set(View.VISIBLE)
                viewModel!!.showRecyclerView!!.set(View.GONE)
                viewModel!!.showString.set("No Data Available")
            } else {
                viewModel!!.showEmpty!!.set(View.GONE)
                viewModel!!.showRecyclerView!!.set(View.VISIBLE)
                if ((it as List<Movie>).isNotEmpty())
                    viewModel!!.setMoviesInAdapter(it)
                else
                    viewModel!!.setMoviesInAdapter(null)
            }
        })
        setupListClick()
    }

    private fun setupListClick() {
        viewModel!!.selected!!.observe(this, { movie ->
            if (movie != null) {
                val intent = Intent(this, PlayTrailerActivity::class.java)
                intent.putExtra("url", movie.url)
                startActivity(intent)
            }
        })
    }
}