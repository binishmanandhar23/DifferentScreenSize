package io.github.binishmanandhar23.differentscreensize.utils

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
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

object Components {

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
                Text(text = bookTitle, maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier.padding(vertical = 3.dp))
                Text(text = bookAuthor, color = Color.LightGray)
            }
        }
    }

    @OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
    @Composable
    fun CustomImage(modifier: Modifier = Modifier, url: String, contentDescription: String? = "", cornerRadius: Dp = 0.dp){
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(url)
                .size(Size.ORIGINAL) // Set the target size to load the image at.
                .build()
        )
        AnimatedContent(targetState = painter.state, modifier = modifier
            .widthIn(min = 50.dp)
            .heightIn(min = 80.dp)) { state ->
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
}