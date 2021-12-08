package fr.cmonapp.android_facebook_player

import android.util.Log

private const val TAG = "FacebookVideoPlayer"

class FacebookPlayer(private val webViewFacebookVideoPlayer: WebViewFacebookVideoPlayer){


    fun play() {
        webViewFacebookVideoPlayer.play()
    }

    fun pause() {
        webViewFacebookVideoPlayer.pause()
    }

    fun seek(seconds: Int) {
        webViewFacebookVideoPlayer.seek(seconds)
    }

    fun mute() {
        webViewFacebookVideoPlayer.mute()
    }

    fun unMute() {
        webViewFacebookVideoPlayer.unMute()
    }

    fun isMuted() : Boolean {
        return webViewFacebookVideoPlayer.isMuted()
    }

    fun setVolume(volume: Float) {
        if(volume < 0 || volume > 1){
            Log.e(TAG, " The volume provided ($volume) is outside the range [0, 1].")
            return
        }
        webViewFacebookVideoPlayer.setVolume(volume)
    }

    fun getVolume() : Float {
        return webViewFacebookVideoPlayer.getVolume()
    }

    fun getCurrentPosition(callback: (seconds: Int) -> Unit) {
        webViewFacebookVideoPlayer.getCurrentPosition(callback)
    }

    fun getDuration() : Int {
        return webViewFacebookVideoPlayer.getDuration()
    }
}