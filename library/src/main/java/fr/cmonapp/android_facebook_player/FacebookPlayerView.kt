package fr.cmonapp.android_facebook_player

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebSettings
import android.webkit.WebView

class FacebookPlayerParameters(val videoUrl: String, val appId: String = ""){
    companion object{
        const val FACEBOOK_VIDEO_URL_REGEX = "https?://.*facebook\\.com/(?:(?:video\\.php|watch\\/).*\\??v=\\d+|.*?/videos/.*/?\\d+)/?.*|https?://fb\\.watch/.*"
    }

    fun checkVideoUrl() : Boolean {
        return videoUrl.matches(FACEBOOK_VIDEO_URL_REGEX.toRegex())
    }
}

class FacebookPlayerView(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    AdaptiveFacebookPlayerFrameLayout(context, attrs, defStyleAttr) {

    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet? = null) : this(context, attrs, 0)

    private val webViewFacebookVideoPlayer: WebViewFacebookVideoPlayer =
        WebViewFacebookVideoPlayer(context)

    init {
        webViewFacebookVideoPlayer.setBackgroundColor(Color.parseColor("#000000"))
        addView(
            webViewFacebookVideoPlayer,
            LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        )
    }

    fun load(params: FacebookPlayerParameters, listener: ((FacebookPlayer) -> Unit)? = null) {
        webViewFacebookVideoPlayer.initialize(params, listener)
    }
}


class WebViewFacebookVideoPlayer constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : WebView(context, attrs, defStyleAttr) {

    private lateinit var facebookPlayerBridge: FacebookPlayerBridge
    private val mainThreadHandler: Handler = Handler(Looper.getMainLooper())

    override fun loadUrl(url: String) {
        mainThreadHandler.post {
            super.loadUrl(url)
        }
    }

    @SuppressLint("SetJavaScriptEnabled", "JavascriptInterface")
    fun initialize(
        params: FacebookPlayerParameters,
        listener: ((FacebookPlayer) -> Unit)? = null
    ) {
        settings.javaScriptEnabled = true
        settings.mediaPlaybackRequiresUserGesture = false
        settings.cacheMode = WebSettings.LOAD_NO_CACHE


        val facebookPlayer = FacebookPlayer(this)

        val facebookPlayerListener = object : FacebookPlayerListener {
            override fun isReadyCallback() {
                listener?.invoke(facebookPlayer)
            }

            override fun startedPlayingCallback() {
            }

            override fun pausedCallback() {
            }

            override fun finishedPlayingCallback() {
            }

            override fun startedBufferingCallback() {
            }

            override fun finishedBufferingCallback() {
            }

            override fun errorCallback() {
            }
        }


        facebookPlayerBridge = FacebookPlayerBridge(facebookPlayerListener)
        addJavascriptInterface(facebookPlayerBridge, "FacebookPlayerBridge")

        val htmlPage =
            resources.openRawResource(R.raw.facebook_player).bufferedReader().use { it.readText() }
                .replace("{your-app-id}", params.appId)
                .replace("{video-url}", params.videoUrl)

        loadDataWithBaseURL("https://www.facebook.com", htmlPage, "text/html", "utf-8", null)
    }


    fun play() {
        loadUrl("javascript:play()")
    }

    fun pause() {
        loadUrl("javascript:pause()")
    }

    fun seek(seconds: Int) {
        loadUrl("javascript:seek($seconds)")
    }

    fun mute() {
        loadUrl("javascript:mute()")
    }

    fun unMute() {
        loadUrl("javascript:unMute()")
    }

    fun isMuted(): Boolean {
        return facebookPlayerBridge.isMute
    }

    fun setVolume(volume: Float) {
        loadUrl("javascript:setVolume($volume)")
    }

    fun getVolume(): Float {
        return facebookPlayerBridge.volume
    }

    fun getCurrentPosition(callback: (seconds: Int) -> Unit) {
        facebookPlayerBridge.currentPositionCallback = callback
        loadUrl("javascript:getCurrentPosition()")
    }

    fun getDuration(): Int {
        return facebookPlayerBridge.duration
    }


    class FacebookPlayerBridge(private val facebookPlayerListener: FacebookPlayerListener? = null) {

        var currentPositionCallback: ((seconds: Int) -> Unit)? = null
        private var isReady = false
        var volume = 1F
        var isMute: Boolean = false
        var duration = 0


        @JavascriptInterface
        fun isReadyCallback() {
            if (!isReady) {
                facebookPlayerListener?.isReadyCallback()
                isReady = true
            }
        }

        @JavascriptInterface
        fun startedPlayingCallback() {
            facebookPlayerListener?.startedPlayingCallback()
        }

        @JavascriptInterface
        fun pausedCallback() {
            facebookPlayerListener?.pausedCallback()
        }

        @JavascriptInterface
        fun finishedPlayingCallback() {
            facebookPlayerListener?.finishedPlayingCallback()
        }

        @JavascriptInterface
        fun startedBufferingCallback() {
            facebookPlayerListener?.startedBufferingCallback()
        }

        @JavascriptInterface
        fun finishedBufferingCallback() {
            facebookPlayerListener?.finishedBufferingCallback()
        }

        @JavascriptInterface
        fun errorCallback() {
            facebookPlayerListener?.errorCallback()
        }

        @JavascriptInterface
        fun setIsMuted(isMute: Boolean) {
            this.isMute = isMute
        }

        @JavascriptInterface
        fun setVolumeCallback(volume: Float) {
            this.volume = volume
        }

        @JavascriptInterface
        fun getCurrentPositionResult(seconds: Int) {
            currentPositionCallback?.invoke(seconds)
        }

        @JavascriptInterface
        fun updateDuration(seconds: Int) {
            duration = seconds
        }

    }

    interface FacebookPlayerListener {
        fun isReadyCallback()
        fun startedPlayingCallback()
        fun pausedCallback()
        fun finishedPlayingCallback()
        fun startedBufferingCallback()
        fun finishedBufferingCallback()
        fun errorCallback()
    }
}
