package fr.cmonapp.android_facebook_player

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.accompanist.flowlayout.FlowRow
import fr.cmonapp.android_facebook_player.ui.theme.AppTheme


class ComposeMainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    var facebookPlayer: FacebookPlayer? = null
                    Column {

                        FacebookPlayerCompose(FacebookPlayerParameters(Constants.VIDEO_URL)) {
                            facebookPlayer = it
                        }
                        FlowRow(crossAxisSpacing = 10.dp, mainAxisSpacing = 10.dp) {
                            Button(onClick = {
                                facebookPlayer?.play()
                            }) {
                                Text(stringResource(R.string.play))
                            }
                            Button(onClick = {
                                facebookPlayer?.pause()
                            }) {
                                Text(stringResource(R.string.pause))
                            }
                            Button(onClick = {
                                facebookPlayer?.seek(30)
                            }) {
                                Text(stringResource(R.string.seek))
                            }
                            Button(onClick = {
                                facebookPlayer?.mute()
                            }) {
                                Text(stringResource(R.string.mute))
                            }
                            Button(onClick = {
                                facebookPlayer?.unMute()
                            }) {
                                Text(stringResource(R.string.un_mute))
                            }
                            Button(onClick = {
                                facebookPlayer?.isMuted()?.let {
                                    Toast.makeText(
                                        applicationContext,
                                        getString(R.string.is_muted_message, it),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                            }) {
                                Text(stringResource(R.string.is_muted))
                            }
                            Button(onClick = {
                                facebookPlayer?.setVolume(0.5F)
                            }) {
                                Text(stringResource(R.string.set_volume))
                            }
                            Button(onClick = {
                                facebookPlayer?.getVolume()?.let {
                                    Toast.makeText(
                                        applicationContext,
                                        getString(R.string.volume_message, it),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }) {
                                Text(stringResource(R.string.get_volume))
                            }
                            Button(onClick = {
                                facebookPlayer?.getCurrentPosition {
                                    Toast.makeText(
                                        applicationContext,
                                        getString(R.string.get_current_position_message, it),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }) {
                                Text(stringResource(R.string.get_current_position))
                            }
                            Button(onClick = {
                                facebookPlayer?.getDuration()?.let {
                                    Toast.makeText(
                                        applicationContext,
                                        getString(R.string.get_duration_message, it),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }) {
                                Text(stringResource(R.string.get_duration))
                            }
                        }
                    }
                }
            }
        }
    }


}

@Composable
fun FacebookPlayerCompose(
    params: FacebookPlayerParameters,
    function: (view: FacebookPlayer) -> Unit
) {
    AndroidView(
        modifier = Modifier.fillMaxWidth(),
        factory = { context ->
            FacebookPlayerView(context)
        },
        update = { view ->
            view.load(params, function)
        }
    )
}