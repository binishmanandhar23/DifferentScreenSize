package io.github.binishmanandhar23.differentscreensize.data

import androidx.annotation.StringRes
import io.github.binishmanandhar23.differentscreensize.R

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object Main : Screen("main", R.string.main)
    object Home : Screen("home", R.string.home)
    object Profile : Screen("profile", R.string.profile)
    object Library : Screen("library", R.string.library)
    object Detail : Screen("detail", R.string.detail)
}
