package io.github.binishmanandhar23.differentscreensize.screens

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.CountDownTimer
import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import io.github.binishmanandhar23.differentscreensize.data.BookDetailData
import io.github.binishmanandhar23.differentscreensize.data.BookListPreviewData
import io.github.binishmanandhar23.differentscreensize.enums.Action
import io.github.binishmanandhar23.differentscreensize.receivers.NotificationActionReceiver
import io.github.binishmanandhar23.differentscreensize.utils.Components
import io.github.binishmanandhar23.differentscreensize.utils.Components.CustomImage
import io.github.binishmanandhar23.differentscreensize.viewmodels.DetailScreenViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import kotlin.random.Random

class DetailScreen(
    private val navController: NavController,
    val detailScreenViewModel: DetailScreenViewModel
) {
    private val bookDetailData = BookDetailData(
        bookListPreviewData = BookListPreviewData(
            bookImageURL = io.github.binishmanandhar23.differentscreensize.utils.Random.getBookThumbnails30(
                Random.nextInt(30000, 30001)
            ),
            bookTitle = "The power of positive thinking",
            bookAuthor = "Binish Mananandhar",
            availableLanguage = "English"
        ), audioUrl = "https://www.kozco.com/tech/piano2.wav"
    )

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    fun Main() {
        val listOfPlaybackSpeed = listOf(0.75f, 1.0f, 1.25f, 1.50f, 1.75f, 2.0f)
        var selectedSpeed by remember { mutableStateOf(1f) }
        val currentTimeFormatted by detailScreenViewModel.currentTime.collectAsState()
        var currentTime by remember { mutableStateOf(0f) }
        var duration by remember{ mutableStateOf(0f) }
        val initializing by detailScreenViewModel.initializing.collectAsState()
        val coroutineScope = rememberCoroutineScope()
        val audioUrl by derivedStateOf {
            bookDetailData.audioUrl
        }
        LaunchedEffect(key1 = audioUrl){
            detailScreenViewModel.currentlyPlayingAudioUrl.value = audioUrl
        }
        DisposableEffect(key1 = initializing) {
            duration = detailScreenViewModel.mediaPlayer.duration.toFloat()
            val countDownTimer: CountDownTimer = object : CountDownTimer(Long.MAX_VALUE, 1000) {
                override fun onTick(p0: Long) {
                    if (!initializing)
                        detailScreenViewModel.updateCurrentTime(detailScreenViewModel.mediaPlayer.currentPosition.toLong())
                            .also {
                                currentTime = detailScreenViewModel.mediaPlayer.currentPosition.toFloat()
                            }
                }

                override fun onFinish() {}
            }.start()
            onDispose {
                countDownTimer.cancel()
            }
        }
        bookDetailData.run {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .background(color = MaterialTheme.colors.background),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.padding(start = 15.dp)
                    ) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Back button",
                            tint = MaterialTheme.colors.onBackground
                        )
                    }
                }
                Divider(
                    thickness = 1.dp,
                    color = Color.LightGray,
                    modifier = Modifier.fillMaxWidth()
                )
                //Book Thumbnail
                CustomImage(
                    modifier = Modifier.padding(top = 50.dp, bottom = 15.dp),
                    url = bookListPreviewData.bookImageURL,
                    contentDescription = bookListPreviewData.bookTitle,
                    cornerRadius = 10.dp
                )
                Text(
                    text = bookListPreviewData.bookTitle,
                    style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(horizontal = 25.dp, vertical = 5.dp)
                )
                Text(
                    text = bookListPreviewData.bookAuthor,
                    style = TextStyle(fontSize = 18.sp, color = Color.LightGray),
                    modifier = Modifier.padding(horizontal = 25.dp)
                )

                //Playback Speed
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    AnimatedContent(targetState = initializing) { loading ->
                        if (!loading)
                            Row(
                                modifier = Modifier
                                    .padding(horizontal = 30.dp, vertical = 10.dp)
                                    .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                listOfPlaybackSpeed.forEach {
                                    Components.PlaybackSpeedButton(
                                        speed = it,
                                        isSelected = selectedSpeed == it
                                    ) { speed ->
                                        selectedSpeed = speed
                                        detailScreenViewModel.mediaPlayer.let { player ->
                                            player.playbackParams =
                                                player.playbackParams.setSpeed(speed)
                                        }
                                    }
                                }
                            }
                    }

                AnimatedContent(targetState = initializing) { loading ->
                    if (!loading)
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 15.dp, vertical = 20.dp)
                                .fillMaxWidth()
                        ) {
                            Components.CustomSeekbar(
                                value = currentTime,
                                onValueChangeFinished = {
                                    coroutineScope.launch {
                                        detailScreenViewModel.mediaPlayer.seekTo(it.toInt())
                                    }
                                },
                                duration = duration
                            )
                            Row(
                                modifier = Modifier
                                    .padding(top = 10.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = currentTimeFormatted,
                                    style = TextStyle(
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Light,
                                        color = MaterialTheme.colors.onBackground
                                    )
                                )
                                Text(
                                    text = detailScreenViewModel.getFormattedTime(duration.toLong()),
                                    style = TextStyle(
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Light,
                                        color = MaterialTheme.colors.onBackground
                                    )
                                )
                            }
                        }
                }
                Components.PlayPauseButton(loading = initializing, play = detailScreenViewModel.mediaPlayer.isPlaying) {
                    if (detailScreenViewModel.mediaPlayer.isPlaying)
                        detailScreenViewModel.mediaPlayer.pause()
                    else detailScreenViewModel.mediaPlayer.start()
                }
            }
        }
    }
}