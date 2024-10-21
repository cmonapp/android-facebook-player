package fr.cmonapp.android_facebook_player.sample

import android.os.Bundle
import android.webkit.WebView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import fr.cmonapp.android_facebook_player.FacebookPlayerParameters
import fr.cmonapp.android_facebook_player.FacebookPlayerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        WebView.setWebContentsDebuggingEnabled(true)
        val facebookPlayerView = findViewById<FacebookPlayerView>(R.id.player)

        val params = FacebookPlayerParameters(videoUrl = Constants.VIDEO_URL)
        if (params.checkVideoUrl()) {

            facebookPlayerView.load(params) { facebookPlayer ->

                findViewById<Button>(R.id.play).setOnClickListener {
                    facebookPlayer.play()
                }
                findViewById<Button>(R.id.pause).setOnClickListener {
                    facebookPlayer.pause()
                }
                findViewById<Button>(R.id.seek).setOnClickListener {
                    facebookPlayer.seek(30)
                }
                findViewById<Button>(R.id.mute).setOnClickListener {
                    facebookPlayer.mute()
                }
                findViewById<Button>(R.id.unMute).setOnClickListener {
                    facebookPlayer.unMute()
                }
                findViewById<Button>(R.id.isMuted).setOnClickListener {
                    val isMuted = facebookPlayer.isMuted()
                    Toast.makeText(
                        this@MainActivity,
                        getString(R.string.is_muted_message, isMuted),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                findViewById<Button>(R.id.setVolume).setOnClickListener {
                    facebookPlayer.setVolume(0.5F)
                }
                findViewById<Button>(R.id.getVolume).setOnClickListener {
                    val volume = facebookPlayer.getVolume()
                    Toast.makeText(
                        this@MainActivity,
                        getString(R.string.volume_message, volume),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                findViewById<Button>(R.id.getCurrentPosition).setOnClickListener {
                    facebookPlayer.getCurrentPosition { seconds ->
                        Toast.makeText(
                            this@MainActivity,
                            getString(R.string.get_current_position_message, seconds),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                findViewById<Button>(R.id.getDuration).setOnClickListener {
                    val duration = facebookPlayer.getDuration()
                    Toast.makeText(
                        this@MainActivity,
                        getString(R.string.get_current_position_message, duration),
                        Toast.LENGTH_SHORT
                    ).show()

                }

            }
        }else{
            Toast.makeText(this@MainActivity, getString(R.string.video_url_is_not_valid), Toast.LENGTH_SHORT).show()
        }
    }
}