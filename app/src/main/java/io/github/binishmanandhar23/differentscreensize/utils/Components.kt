package io.github.binishmanandhar23.differentscreensize.utils

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import io.github.binishmanandhar23.differentscreensize.data.BookListPreviewData
import io.github.binishmanandhar23.differentscreensize.data.CustomNavigationDrawer

object Components {

    @Composable
    fun CustomNavigationDrawer(
        modifier: Modifier = Modifier,
        listOfItems: List<CustomNavigationDrawer>,
        onClick: ((customNavigationDrawer: CustomNavigationDrawer) -> Unit)? = null
    ) {
        val hapticFeedback = LocalHapticFeedback.current
        Column(
            modifier = modifier
                .padding(top = 20.dp)
                .fillMaxHeight(), verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            listOfItems.forEach { drawerItems ->
                drawerItems.customNavigationDrawerItems.run {
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .fillMaxWidth()
                            .clickable(MutableInteractionSource(), null) {
                                hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                                onClick?.invoke(drawerItems)
                            }, verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            if (isSelected) iconSelected else iconUnSelected,
                            contentDescription = stringResource(id = title),
                            tint = if (isSelected) MaterialTheme.colors.primary else Color.Gray
                        )
                        Text(
                            stringResource(title),
                            color = if (isSelected) MaterialTheme.colors.primary else Color.Gray,
                            modifier = Modifier.padding(start = 25.dp),
                            style = TextStyle(fontSize = 20.sp)
                        )
                        Spacer(modifier = Modifier.size(10.dp))
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun BookListPreview(bookListPreviewData: BookListPreviewData?, onClick: (() -> Unit)? = null) {
        val hapticFeedback = LocalHapticFeedback.current
        bookListPreviewData?.run {
            Column(modifier = Modifier
                .widthIn(max = 120.dp)
                .clickable(MutableInteractionSource(), null) {
                    hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                    onClick?.invoke()
                }) {
                Card(
                    shape = RoundedCornerShape(10.dp),
                    elevation = CardDefaults.cardElevation(5.dp),
                ) {
                    Box {
                        CustomImage(url = bookImageURL, contentDescription = bookTitle)
                        if (availableLanguage != null)
                            Box(
                                modifier = Modifier
                                    .padding(top = 4.dp)
                                    .align(Alignment.TopEnd)
                                    .background(
                                        color = Color.White,
                                        shape = RoundedCornerShape(
                                            topStart = 40.dp,
                                            bottomStart = 40.dp
                                        )
                                    )
                            ) {
                                Text(
                                    availableLanguage,
                                    modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                                    style = TextStyle(fontSize = 12.sp)
                                )
                            }
                    }
                }
                Text(
                    text = bookTitle,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(vertical = 3.dp)
                )
                Text(text = bookAuthor, color = Color.LightGray)
            }
        }
    }

    @OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
    @Composable
    fun CustomImage(
        modifier: Modifier = Modifier,
        url: String,
        contentDescription: String? = "",
        cornerRadius: Dp = 0.dp
    ) {
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(url)
                .size(Size.ORIGINAL) // Set the target size to load the image at.
                .build()
        )
        AnimatedContent(
            targetState = painter.state, modifier = modifier
                .widthIn(min = 50.dp)
                .heightIn(min = 80.dp)
        ) { state ->
            Card(
                shape = RoundedCornerShape(cornerRadius),
                elevation = CardDefaults.cardElevation(5.dp),
            ) {
                if (state is AsyncImagePainter.State.Success)
                    Image(painter = painter, contentDescription = contentDescription)
                else
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(20.dp)
                            .size(30.dp)
                    )
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
    @Composable
    fun PlaybackSpeedButton(
        speed: Float,
        isSelected: Boolean = false,
        onClick: ((speed: Float) -> Unit)
    ) {
        val hapticFeedback = LocalHapticFeedback.current
        AnimatedContent(targetState = isSelected, transitionSpec = {
            scaleIn(tween(300)) with scaleOut(tween(300))
        }) { selected ->
            Card(
                shape = RoundedCornerShape(5.dp),
                elevation = CardDefaults.cardElevation(if (selected) 5.dp else 1.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colors.background),
                border = BorderStroke(
                    1.dp,
                    color = if (selected) MaterialTheme.colors.primary else MaterialTheme.colors.onBackground
                ),
                onClick = {
                    hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                    onClick(speed)
                }
            ) {
                Box {
                    Text(
                        text = "${speed}X",
                        style = TextStyle(fontSize = 12.sp),
                        color = MaterialTheme.colors.onBackground,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp)
                    )
                }
            }
        }
    }

    @Composable
    fun CustomSeekbar(
        modifier: Modifier = Modifier,
        value: Float,
        duration: Float,
        onValueChangeFinished: (newValue: Float) -> Unit
    ) {
        var defaultValue by remember(value) { mutableStateOf(value) }
        Slider(
            value = defaultValue,
            onValueChange = { defaultValue = it },
            onValueChangeFinished = { onValueChangeFinished(defaultValue) },
            valueRange = 0f..duration,
            modifier = modifier
        )
    }

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
    @Composable
    fun PlayPauseButton(
        loading: Boolean = false,
        play: Boolean,
        onClick: (isPlaying: Boolean) -> Unit
    ) {
        val hapticFeedback = LocalHapticFeedback.current
        var defaultValue by remember { mutableStateOf(play) }
        AnimatedContent(targetState = defaultValue) { isPlaying ->
            Card(
                shape = CircleShape,
                elevation = CardDefaults.cardElevation(3.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colors.background),
                border = BorderStroke(1.dp, color = MaterialTheme.colors.onBackground),
                onClick = {
                    hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                    defaultValue = !defaultValue
                    onClick(isPlaying)
                }
            ) {
                Box {
                    if (loading)
                        CircularProgressIndicator()
                    else
                        Icon(
                            if (!isPlaying) Icons.Default.PlayArrow else Icons.Default.Star,
                            contentDescription = "Play button",
                            modifier = Modifier.padding(10.dp),
                            tint = MaterialTheme.colors.onBackground
                        )
                }
            }
        }
    }
}