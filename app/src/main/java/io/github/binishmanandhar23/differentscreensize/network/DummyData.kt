package io.github.binishmanandhar23.differentscreensize.network

import io.github.binishmanandhar23.differentscreensize.data.BookDetailData
import io.github.binishmanandhar23.differentscreensize.data.BookListPreviewData
import kotlin.random.Random

object DummyData {
    val bookDetailData = BookDetailData(
        bookListPreviewData = BookListPreviewData(
            bookImageURL = io.github.binishmanandhar23.differentscreensize.utils.Random.getBookThumbnails30(
                Random.nextInt(30000, 30001)
            ),
            bookTitle = "The power of positive thinking",
            bookAuthor = "Binish Mananandhar",
            availableLanguage = "English"
        ), audioUrl = "https://www.kozco.com/tech/piano2.wav"
    )
}