# android-facebook-player

[![](https://jitpack.io/v/cmonapp/android-facebook-player.svg)](https://jitpack.io/#cmonapp/android-facebook-player)

Android Facebook Player is a simple Facebook Player for Android.

The library use [API Embedded Video Player](https://developers.facebook.com/docs/plugins/embedded-video-player/api) inside of WebView.


Add it to your build.gradle with:
```gradle
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```
and:

```gradle
dependencies {
    compile 'fr.cmonapp:android-facebook-player:{latest version}'
}
```

## Usage


```xml

  <fr.cmonapp.android_facebook_player.FacebookPlayerView
            android:id="@+id/player"
            android:layout_width="match_parent"
            android:layout_height="wrap_content" />

```

```kotlin

val facebookPlayerView = findViewById<FacebookPlayerView>(R.id.player)

val params = FacebookPlayerParameters("https://www.facebook.com/facebook/videos/10153231379946729/")
if (params.checkVideoUrl()) {
    facebookPlayerView.load(params) {  facebookPlayer ->
        // Player is loaded
        
        //Examples usage
        facebookPlayer.play()

        facebookPlayer.pause()

        facebookPlayer.seek(30)

        facebookPlayer.mute()

        facebookPlayer.unMute()

        facebookPlayer.isMuted()

        facebookPlayer.setVolume(0.5F)
        
        facebookPlayer.getVolume()

        facebookPlayer.getCurrentPosition {  seconds ->
            
        }
        
        facebookPlayer.getDuration()
    
    }
}

```