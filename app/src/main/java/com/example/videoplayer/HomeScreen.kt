package com.example.videoplayer

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.MediaMetadata
import androidx.media3.common.MimeTypes
import androidx.media3.common.PlaybackException
import androidx.media3.exoplayer.analytics.AnalyticsListener
import androidx.navigation.NavHostController
import com.example.videoplayer.components.AlertDialogViewComponent
import com.example.videoplayer.components.TopBarView
import io.sanghun.compose.video.RepeatMode
import io.sanghun.compose.video.VideoPlayer
import io.sanghun.compose.video.controller.VideoPlayerControllerConfig
import io.sanghun.compose.video.toRepeatMode
import io.sanghun.compose.video.uri.VideoPlayerMediaItem

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navHostController: NavHostController) {
    var openDialog by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    var videoUrlField by remember { mutableStateOf("") }
    val mainViewModel: MainViewModel = viewModel()
    Scaffold(topBar = {
        TopBarView({
            navHostController.navigate("about_screen")
        }, {
            openDialog = true
        })
    }) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp),
            ) {
                VideoView(
                    mainViewModel.currentVideoUrl,
                    mainViewModel
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = { mainViewModel.changeVideoUrl = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
                ) {
                    Text(text = "Play RAW Video")
                }

                Spacer(modifier = Modifier.height(20.dp))

                TextField(
                    value = videoUrlField,
                    onValueChange = { videoUrlField = it },
                    placeholder = {
                        Text(
                            text = "Enter URL"
                        )
                    })

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        mainViewModel.changeVideoUrl = false
                        mainViewModel.changeVideoUrl(videoUrlField)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
                ) {
                    Text(text = "Play Video")
                }
            }
            if (openDialog) {
                AlertDialogViewComponent({
                    openDialog = false
                }, {
                    Toast.makeText(context, "Welcome to Home Screen", Toast.LENGTH_SHORT).show()
                    openDialog = false
                })
            }
        }

    }
}


@Composable
fun VideoView(currentVideoUrl: String, mainViewModel: MainViewModel) {
    var repeatMode by remember { mutableStateOf(RepeatMode.NONE) }


    val rawType = listOf(
        VideoPlayerMediaItem.RawResourceMediaItem(
            resourceId = R.raw.test_video,
        ),
    )

    val urlType = listOf(
        VideoPlayerMediaItem.NetworkMediaItem(
            url = currentVideoUrl,
            mediaMetadata = MediaMetadata.Builder().setTitle("Clear MP4: Dizzy")
                .build(),
            mimeType = MimeTypes.VIDEO_MP4,
        ),
    )


    Column {



        VideoPlayer(
            mediaItems = if (mainViewModel.changeVideoUrl) rawType else urlType,
            handleLifecycle = true,
            autoPlay = false,
            usePlayerController = true,
            enablePipWhenBackPressed = false,
            enablePip = false,
            controllerConfig = VideoPlayerControllerConfig.Default.copy(
                showSubtitleButton = false,
                showNextTrackButton = true,
                showBackTrackButton = true,
                showBackwardIncrementButton = false,
                showForwardIncrementButton = false,
                showRepeatModeButton = false,
                showFullScreenButton = false,
            ),
            repeatMode = repeatMode,
            onCurrentTimeChanged = {
                Log.e("CurrentTime", it.toString())
            },
            playerInstance = {
                Log.e("VOLUME", volume.toString())
                addAnalyticsListener(object : AnalyticsListener {
                    @SuppressLint("UnsafeOptInUsageError")
                    override fun onRepeatModeChanged(
                        eventTime: AnalyticsListener.EventTime,
                        rMode: Int,
                    ) {
                        repeatMode = rMode.toRepeatMode()

                    }

                    @SuppressLint("UnsafeOptInUsageError")
                    override fun onPlayWhenReadyChanged(
                        eventTime: AnalyticsListener.EventTime,
                        playWhenReady: Boolean,
                        reason: Int,
                    ) {

                    }

                    @SuppressLint("UnsafeOptInUsageError")
                    override fun onVolumeChanged(
                        eventTime: AnalyticsListener.EventTime,
                        volume: Float,
                    ) {

                    }

                    @SuppressLint("UnsafeOptInUsageError")
                    override fun onPlayerError(
                        eventTime: AnalyticsListener.EventTime,
                        error: PlaybackException
                    ) {
                        Log.d("onPlayerError", error.toString())
                        // super.onPlayerError(eventTime, error)
                    }
                })
            },
            modifier = Modifier
                .fillMaxWidth(),
        )

    }


}