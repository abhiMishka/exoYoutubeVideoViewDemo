package com.yusufcakmak.exoplayersample

import android.os.Bundle
import android.view.KeyEvent
import android.view.KeyEvent.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.android.synthetic.main.activity_youtubeview.*


class YoutubeActivity : AppCompatActivity() {

    var mYouTubePlayer: YouTubePlayer? = null
    var currentVolume = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_youtubeview)
        val youTubePlayerView = findViewById<YouTubePlayerView>(R.id.youtube_player_view)
//        lifec.addObserver(youTubePlayerView)


        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                mYouTubePlayer = youTubePlayer
                val videoId = getString(R.string.video_id)
                youTubePlayer.loadVideo(videoId, 0f)
                youTubePlayer.setVolume(currentVolume)
            }
        })

        emptyView.setOnClickListener {
            Toast.makeText(baseContext,"Clicked!",Toast.LENGTH_SHORT).show()
        }

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KEYCODE_VOLUME_DOWN) {
            if(currentVolume>=10) {
                currentVolume = currentVolume - 10
                mYouTubePlayer?.setVolume(currentVolume)
            }
        }else  if (keyCode == KEYCODE_VOLUME_UP) {
            if(currentVolume<=90) {
                currentVolume = currentVolume + 10
                mYouTubePlayer?.setVolume(currentVolume)
            }
        } else if(keyCode == KEYCODE_BACK){
            finish()
        }
        return true
    }

}
