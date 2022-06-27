package io.github.binishmanandhar23.differentscreensize.utils

import java.text.DecimalFormat

object AppUtil {
    fun Int.twoDecimalFormat(): String = DecimalFormat("00").format(this)
}