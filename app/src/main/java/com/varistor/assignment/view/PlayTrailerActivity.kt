package com.varistor.assignment.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.util.SparseArray
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import at.huber.youtubeExtractor.VideoMeta
import at.huber.youtubeExtractor.YouTubeExtractor
import at.huber.youtubeExtractor.YtFile
import com.google.android.exoplayer2.ui.PlayerView
import com.varistor.assignment.R
import com.varistor.assignment.databinding.ActivityPlayTrailerBinding
import com.varistor.assignment.utils.Common
import com.varistor.assignment.utils.ExoPlayerManager


class PlayTrailerActivity : AppCompatActivity() {
    private lateinit var playTrailerBinding: ActivityPlayTrailerBinding
    private lateinit var context: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        playTrailerBinding = DataBindingUtil.setContentView(this, R.layout.activity_play_trailer)
        context = this
        Common.setToolbarWithBackAndTitle(context, "Movies", false, R.drawable.ic_backbutton)
        if(intent!=null)
        extractYoutubeUrl(intent.getStringExtra("url").toString())
        playTrailerBinding.mToolbar.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        if(ExoPlayerManager.getSharedInstance(this).isPlayerPlaying)
        {
            ExoPlayerManager.getSharedInstance(this).stopPlayer(true)
        }
        super.onBackPressed()
    }

    private fun extractYoutubeUrl(mYoutubeLink: String) {
        @SuppressLint("StaticFieldLeak") val mExtractor: YouTubeExtractor =
            object : YouTubeExtractor(this) {
                override fun onExtractionComplete(
                    sparseArray: SparseArray<YtFile>?,
                    videoMeta: VideoMeta?
                ) {
                    Log.e("URL",sparseArray.toString())
                    if (sparseArray != null) {
                        playVideo(sparseArray[22].url)
                    }
                }
            }
        mExtractor.extract(mYoutubeLink, true, true)
    }

    private fun playVideo(downloadUrl: String) {
        playTrailerBinding.mPlayerView.player = ExoPlayerManager.getSharedInstance(this).playerView.player
        ExoPlayerManager.getSharedInstance(this).playStream(downloadUrl)
    }
}