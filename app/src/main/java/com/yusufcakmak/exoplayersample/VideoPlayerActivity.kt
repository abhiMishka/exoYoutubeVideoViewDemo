package com.yusufcakmak.exoplayersample

import android.app.Activity
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.*
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.activity_video_player.*


class VideoPlayerActivity : Activity() {

    private lateinit var player: SimpleExoPlayer
    private lateinit var mediaDataSourceFactory: DataSource.Factory

    private var trackSelector: DefaultTrackSelector? = null
    private var lastSeenTrackGroupArray: TrackGroupArray? = null
    private val videoTrackSelectionFactory = AdaptiveTrackSelection.Factory()
    private var currentWindow: Int = 0
    private var playbackPosition: Long = 0
    private val ivHideControllerButton: ImageView by lazy { findViewById<ImageView>(R.id.exo_controller) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)
    }

    private fun initializePlayer() {

        val bandwidthMeter = DefaultBandwidthMeter()
        val userAgent = "locoExoPlayer"

        val httpDataSourceFactory = DefaultHttpDataSourceFactory(userAgent, bandwidthMeter, DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS, DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS, true)

        trackSelector = DefaultTrackSelector(videoTrackSelectionFactory)
        mediaDataSourceFactory = DefaultDataSourceFactory(this, bandwidthMeter, httpDataSourceFactory)

        val mediaSource = ExtractorMediaSource.Factory(mediaDataSourceFactory)
                .createMediaSource(Uri.parse(getString(R.string.video_url)))

        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector)


        with(player) {
            prepare(mediaSource, false, false)
            playWhenReady = true
        }

        playerView.setShutterBackgroundColor(Color.TRANSPARENT)
        playerView.player = player
        playerView.requestFocus()
        ivHideControllerButton.setOnClickListener { playerView.hideController() }

        lastSeenTrackGroupArray = null
    }


    private fun updateStartPosition() {

        with(player) {
            playbackPosition = currentPosition
            currentWindow = currentWindowIndex
            playWhenReady = playWhenReady
        }
    }

    private fun releasePlayer() {
        updateStartPosition()
        player.release()
        trackSelector = null
    }

    public override fun onStart() {
        super.onStart()

        if (Util.SDK_INT > 23) initializePlayer()
    }

    public override fun onResume() {
        super.onResume()

        if (Util.SDK_INT <= 23) initializePlayer()
    }

    public override fun onPause() {
        super.onPause()

        if (Util.SDK_INT <= 23) releasePlayer()
    }

    public override fun onStop() {
        super.onStop()

        if (Util.SDK_INT > 23) releasePlayer()
    }

}