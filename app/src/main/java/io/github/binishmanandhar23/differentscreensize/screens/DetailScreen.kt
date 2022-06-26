package io.github.binishmanandhar23.differentscreensize.screens

import android.media.MediaPlayer
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import io.github.binishmanandhar23.differentscreensize.utils.Components.CustomImage
import java.io.IOException
import kotlin.random.Random

class DetailScreen(val navController: NavController) {
    @Composable
    fun Main() {
        val bookDetailData = BookDetailData(
            bookListPreviewData = BookListPreviewData(
                bookImageURL = io.github.binishmanandhar23.differentscreensize.utils.Random.getBookThumbnails30(
                    Random.nextInt(30000, 30900)
                ),
                bookTitle = "The power of positive thinking",
                bookAuthor = "Binish Mananandhar",
                availableLanguage = "English"
            ), audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"
        )
        bookDetailData.run {
            /*MediaPlayer().apply {
                try {
                    setDataSource(audioUrl)
                    prepare()
                    start()
                } catch (exception: IOException) {
                    exception.printStackTrace()
                }
            }*/

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.padding(start = 15.dp)
                    ) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back button")
                    }
                }
                Divider(
                    thickness = 1.dp,
                    color = Color.LightGray,
                    modifier = Modifier.fillMaxWidth()
                )
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
            }
        }
    }
}