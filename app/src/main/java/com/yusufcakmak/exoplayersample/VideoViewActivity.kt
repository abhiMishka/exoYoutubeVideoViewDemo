package com.yusufcakmak.exoplayersample

import android.content.Context
import android.media.MediaPlayer
import android.widget.MediaController;
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_video_view.*
import android.media.AudioManager
import android.content.Context.AUDIO_SERVICE
import androidx.core.content.ContextCompat.getSystemService



class VideoViewActivity : AppCompatActivity(), MediaPlayer.OnPreparedListener {
    override fun onPrepared(mp: MediaPlayer?) {
        mp?.let { enableSound(0, it) }
        videoView.start()
    }

    private var mediaController: MediaController? = null

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_view)

        videoView.setVideoURI(Uri.parse(getString(R.string.video_url)))
        mediaController = MediaController(this)
        mediaController?.setAnchorView(videoView)
        videoView.setMediaController(mediaController)
        videoView.setOnPreparedListener(this)

    }

    fun enableSound(sound: Int, mp: MediaPlayer) {
        val f = java.lang.Float.valueOf(sound.toFloat())
        mp.setVolume(f, f)
        mp.isLooping = true
        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) //Max Volume 15
        audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)  //this will return current volume.

        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, sound, AudioManager.FLAG_PLAY_SOUND)   //here you can set custom volume.
    }

}
