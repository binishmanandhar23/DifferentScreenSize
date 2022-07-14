package io.github.binishmanandhar23.differentscreensize.viewmodels

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.CountDownTimer
import android.os.Looper
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.binishmanandhar23.differentscreensize.utils.AppUtil.twoDecimalFormat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.URL

class DetailScreenViewModel : ViewModel() {
    private var _initializing = MutableStateFlow(false)
    val initializing = _initializing.asStateFlow()
    fun isLoading() = _initializing.update { true }
    fun done() = _initializing.update { false }

    val currentlyPlayingAudioUrl = MutableLiveData<String>()

    val mediaPlayer by lazy { MediaPlayer() }

    private var _currentTime = MutableStateFlow("00:00")
    val currentTime = _currentTime.asStateFlow()
    fun updateCurrentTime(durationMillis: Long) = viewModelScope.launch {
        _currentTime.update { getFormattedTime(durationMillis) }
    }

    fun getFormattedTime(durationMillis: Long): String{
        val minutes = (durationMillis / 1000) / 60
        val seconds = (durationMillis / 1000) % 60
        return "${minutes.toInt().twoDecimalFormat()}:${seconds.toInt().twoDecimalFormat()}"
    }


    fun getBitmapFromUrl(url: String): MutableLiveData<Bitmap>{
        val bitmapMutable = MutableLiveData<Bitmap>()
        viewModelScope.launch(Dispatchers.IO) {
            val icon = try {
                URL(url).let {
                    BitmapFactory.decodeStream(it.openConnection().getInputStream())
                }
            } catch (ex: IOException) {
                ex.printStackTrace()
                null
            }
            bitmapMutable.postValue(icon)
        }
        return bitmapMutable
    }

}