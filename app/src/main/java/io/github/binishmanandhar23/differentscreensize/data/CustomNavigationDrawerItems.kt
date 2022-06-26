package io.github.binishmanandhar23.differentscreensize.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector

data class CustomNavigationDrawerItems(@StringRes val title: Int, val iconUnSelected: ImageVector,val iconSelected: ImageVector, val isSelected: Boolean = false)
