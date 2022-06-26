package io.github.binishmanandhar23.differentscreensize.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import io.github.binishmanandhar23.differentscreensize.data.BookListData
import io.github.binishmanandhar23.differentscreensize.data.BookListPreviewData
import io.github.binishmanandhar23.differentscreensize.data.Screen
import io.github.binishmanandhar23.differentscreensize.utils.Components
import io.github.binishmanandhar23.differentscreensize.utils.Random

class HomeScreen(val navController: NavController) {
    @Composable
    fun Main() {
        val listOfSections = listOf(
            BookListData(
                title = "Free Summaries", optionText = "Explore all", bookListPreviewData = listOf(
                    BookListPreviewData(
                        bookImageURL = "https://covers.openlibrary.org/b/isbn/9780385533225-L.jpg",
                        bookTitle = "The power of positive thinking",
                        bookAuthor = "Binish Mananandhar",
                        availableLanguage = "English"
                    ),
                    BookListPreviewData(
                        bookImageURL = Random.getBookThumbnails10(kotlin.random.Random.nextInt(10000,10100)),
                        bookTitle = "The new power",
                        bookAuthor = "Binish Mananandhar",
                    ),
                    BookListPreviewData(
                        bookImageURL = Random.getBookThumbnails10(kotlin.random.Random.nextInt(10000,10300)),
                        bookTitle = "From Wikipedia",
                        bookAuthor = "Roman Reigns",
                    ),
                    BookListPreviewData(
                        bookImageURL = Random.getBookThumbnails10(kotlin.random.Random.nextInt(10000,10900)),
                        bookTitle = "The power of positive thinking",
                        bookAuthor = "Binish Mananandhar",
                        availableLanguage = "English"
                    ),
                    BookListPreviewData(
                        bookImageURL = Random.getBookThumbnails10(kotlin.random.Random.nextInt(10000,10400)),
                        bookTitle = "The new power",
                        bookAuthor = "Binish Mananandhar",
                    ),
                    BookListPreviewData(
                        bookImageURL = Random.getBookThumbnails10(kotlin.random.Random.nextInt(10000,10800)),
                        bookTitle = "From Wikipedia",
                        bookAuthor = "Roman Reigns",
                    )
                )
            ),
            BookListData(
                title = "For You", optionText = "Explore all", bookListPreviewData = listOf(
                    BookListPreviewData(
                        bookImageURL = "https://covers.openlibrary.org/b/isbn/9780385533225-L.jpg",
                        bookTitle = "The power of positive thinking",
                        bookAuthor = "Binish Mananandhar",
                        availableLanguage = "English"
                    ),
                    BookListPreviewData(
                        bookImageURL = Random.getBookThumbnails20(kotlin.random.Random.nextInt(20000,20800)),
                        bookTitle = "The new power",
                        bookAuthor = "Binish Mananandhar",
                        availableLanguage = "Nepali"
                    ),
                    BookListPreviewData(
                        bookImageURL = "https://archive.org/download/l_covers_0000/l_covers_0000_05.tar/0000050071-L.jpg",
                        bookTitle = "From Wikipedia",
                        bookAuthor = "Roman Reigns",
                    ),
                    BookListPreviewData(
                        bookImageURL = "https://archive.org/download/l_covers_0000/l_covers_0000_08.tar/0000080076-L.jpg",
                        bookTitle = "The power of positive thinking",
                        bookAuthor = "Binish Mananandhar",
                        availableLanguage = "English"
                    ),
                    BookListPreviewData(
                        bookImageURL = "https://archive.org/download/l_covers_0000/l_covers_0000_07.tar/0000070077-L.jpg",
                        bookTitle = "The new power",
                        bookAuthor = "Binish Mananandhar",
                    ),
                    BookListPreviewData(
                        bookImageURL = "https://archive.org/download/l_covers_0000/l_covers_0000_09.tar/0000090179-L.jpg",
                        bookTitle = "From Wikipedia",
                        bookAuthor = "Roman Reigns",
                    )
                )
            ),
            BookListData(
                title = "Fictional", optionText = "Explore all", bookListPreviewData = listOf(
                    BookListPreviewData(
                        bookImageURL = Random.getBookThumbnails30(kotlin.random.Random.nextInt(30000,30900)),
                        bookTitle = "The power of positive thinking",
                        bookAuthor = "Binish Mananandhar",
                        availableLanguage = "English"
                    ),
                    BookListPreviewData(
                        bookImageURL = Random.getBookThumbnails30(kotlin.random.Random.nextInt(30000,30100)),
                        bookTitle = "The new power",
                        bookAuthor = "Binish Mananandhar",
                    ),
                    BookListPreviewData(
                        bookImageURL = Random.getBookThumbnails30(kotlin.random.Random.nextInt(30000,30300)),
                        bookTitle = "From Wikipedia",
                        bookAuthor = "Roman Reigns",
                    ),
                    BookListPreviewData(
                        bookImageURL = Random.getBookThumbnails30(kotlin.random.Random.nextInt(30000,30300)),
                        bookTitle = "The power of positive thinking",
                        bookAuthor = "Binish Mananandhar",
                        availableLanguage = "English"
                    ),
                    BookListPreviewData(
                        bookImageURL = Random.getBookThumbnails30(kotlin.random.Random.nextInt(30000,30400)),
                        bookTitle = "The new power",
                        bookAuthor = "Binish Mananandhar",
                    ),
                    BookListPreviewData(
                        bookImageURL = Random.getBookThumbnails30(kotlin.random.Random.nextInt(30000,30800)),
                        bookTitle = "From Wikipedia",
                        bookAuthor = "Roman Reigns",
                    )
                )
            ),
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.background)
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .fillMaxWidth()
                    .padding(top = 15.dp, bottom = 10.dp)
            ) {
                Text("AffixAudio", modifier = Modifier.weight(0.7f))
                Row(modifier = Modifier.weight(0.3f), horizontalArrangement = Arrangement.End) {
                    Icon(
                        Icons.Default.Face,
                        contentDescription = "Language change",
                        tint = Color.Black
                    )
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "Language change",
                        tint = Color.Black,
                        modifier = Modifier.padding(horizontal = 14.dp)
                    )
                    Icon(
                        Icons.Default.Menu,
                        contentDescription = "Language change",
                        tint = Color.Black
                    )
                }
            }
            Divider(thickness = 1.dp, color = Color.LightGray, modifier = Modifier.fillMaxWidth())
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)){
                item { Spacer(modifier = Modifier.size(10.dp)) }
                items(listOfSections){
                    BookList(bookListData = it)
                }
            }
        }
    }

    @Composable
    private fun BookList(bookListData: BookListData) {
        bookListData.run {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .padding(bottom = 10.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = bookListData.title,
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    )
                    Text(
                        text = bookListData.optionText,
                        style = TextStyle(fontWeight = FontWeight.Light, fontSize = 14.sp)
                    )
                }
                LazyRow (horizontalArrangement = Arrangement.spacedBy(20.dp)){
                    item{ Spacer(modifier = Modifier.size(0.dp))}
                    items(bookListPreviewData) { data ->
                        Components.BookListPreview(bookListPreviewData = data){
                            navController.navigate(Screen.Detail.route){
                                launchSingleTop = true
                            }
                        }
                    }
                    item{ Spacer(modifier = Modifier.size(0.dp))}
                }
            }
        }
    }
}