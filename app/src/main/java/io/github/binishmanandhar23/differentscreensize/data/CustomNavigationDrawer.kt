package io.github.binishmanandhar23.differentscreensize.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import io.github.binishmanandhar23.differentscreensize.R

sealed class CustomNavigationDrawer(val customNavigationDrawerItems: CustomNavigationDrawerItems){
    object Home: CustomNavigationDrawer(CustomNavigationDrawerItems(title = R.string.home, iconUnSelected = Icons.Default.Home, iconSelected = Icons.Filled.Home))
    object Library: CustomNavigationDrawer(CustomNavigationDrawerItems(title = R.string.library, iconUnSelected = Icons.Default.List, iconSelected = Icons.Filled.List))
    object Profile: CustomNavigationDrawer(CustomNavigationDrawerItems(title = R.string.profile, iconUnSelected = Icons.Default.Person, iconSelected = Icons.Filled.Person))
}
