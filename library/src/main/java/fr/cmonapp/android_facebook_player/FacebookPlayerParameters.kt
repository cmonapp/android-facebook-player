package fr.cmonapp.android_facebook_player

class FacebookPlayerParameters(val videoUrl: String, val appId: String = ""){
    fun checkVideoUrl() : Boolean {
        val videoUrlRegEx = "https?://(?:www|m)\\.facebook\\.com/(?:(?:video\\.php|watch\\/).*\\??v=\\d+|.*?/videos/\\d+)/?.*".toRegex()
        return videoUrl.matches(videoUrlRegEx)
    }
}