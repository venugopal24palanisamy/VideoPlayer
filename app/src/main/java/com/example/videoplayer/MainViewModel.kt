package com.example.videoplayer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.videoplayer.utils.Constants

class MainViewModel() : ViewModel() {

    var currentVideoUrl by mutableStateOf("https://html5demos.com/assets/dizzy.mp4")
    var changeVideoUrl by mutableStateOf(true)

    fun changeVideoUrl(newUrl: String) {
        currentVideoUrl = if (newUrl.isEmpty()) Constants.VIDEO_URL else newUrl
    }
}