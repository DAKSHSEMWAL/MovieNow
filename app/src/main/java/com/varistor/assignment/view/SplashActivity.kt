package com.varistor.assignment.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.varistor.assignment.R

class SplashActivity : AppCompatActivity() {
    private var handler: Handler? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(0, 0)
        setContentView(R.layout.activity_splash)
        initialize()
        startTimer()
    }

    /**
     * method to initialize all the variables and views
     */
    private fun initialize() {
        handler = Handler(Looper.getMainLooper())
    }

    /**
     * method to start splash timer
     */
    private fun startTimer() {
        handler!!.postDelayed({ gotoLandingScreen() }, SPLASH_TIMER)
    }

    private fun gotoLandingScreen() {
        startActivity(Intent(this, MainActivity::class.java))
        finishAffinity()
    }

    override fun onBackPressed() {
        if (handler != null) handler!!.removeCallbacksAndMessages(null)
        super.onBackPressed()
    }

    companion object {
        private const val SPLASH_TIMER: Long = 1500
    }
}