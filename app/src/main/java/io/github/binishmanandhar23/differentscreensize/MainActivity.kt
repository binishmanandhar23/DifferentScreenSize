package io.github.binishmanandhar23.differentscreensize

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import io.github.binishmanandhar23.differentscreensize.data.CustomNavigationDrawer
import io.github.binishmanandhar23.differentscreensize.data.Screen
import io.github.binishmanandhar23.differentscreensize.screens.DetailScreen
import io.github.binishmanandhar23.differentscreensize.screens.HomeScreen
import io.github.binishmanandhar23.differentscreensize.ui.theme.DifferentScreenSizeTheme
import io.github.binishmanandhar23.differentscreensize.utils.Components
import io.github.binishmanandhar23.differentscreensize.viewmodels.DetailScreenViewModel

class MainActivity : ComponentActivity() {
    private val detailScreenViewModel by viewModels<DetailScreenViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DifferentScreenSizeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainBody()
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class, ExperimentalMaterial3Api::class)
    @Composable
    private fun MainBody() {
        val navController = rememberNavController()
        val hapticFeedback = LocalHapticFeedback.current
        val items = listOf(Screen.Home, Screen.Library, Screen.Profile)
        val windowSizeClass = calculateWindowSizeClass(this)
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        val drawerStateOpen by derivedStateOf {
            when (currentDestination?.route) {
                Screen.Home.route, Screen.Profile.route, Screen.Library.route -> true
                else -> false
            }
        }
        /*LaunchedEffect(key1 = currentDestination?.route){
            if(currentDestination?.route != Screen.Detail.route)
                detailScreenViewModel.reset()
        }*/
        val expandedDrawerSize by animateDpAsState(targetValue = if (drawerStateOpen) 280.dp else 0.dp)
        val mediumDrawerSize by animateDpAsState(targetValue = if (drawerStateOpen) 100.dp else 0.dp)
        val compactDrawerSize by animateDpAsState(targetValue = if (drawerStateOpen) 70.dp else 0.dp)
        Box(modifier = Modifier.fillMaxSize()) {
            Row {
                when (windowSizeClass.widthSizeClass) {
                    WindowWidthSizeClass.Medium -> {
                        NavigationRail(backgroundColor = MaterialTheme.colors.background, modifier = Modifier.width(mediumDrawerSize)) {
                            Spacer(modifier = Modifier.size(20.dp))
                            items.forEach { screen ->
                                val isSelected = currentDestination?.route == screen.route
                                Column(horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                        .padding(horizontal = 20.dp)
                                        .clickable(
                                            MutableInteractionSource(), null
                                        ) {
                                            postClick(screen, hapticFeedback, navController)
                                        }) {
                                    Icon(
                                        when (screen) {
                                            Screen.Home -> if (isSelected) Icons.Filled.Home else Icons.Default.Home
                                            Screen.Library -> if (isSelected) Icons.Filled.List else Icons.Default.List
                                            Screen.Profile -> if (isSelected) Icons.Filled.Person else Icons.Default.Person
                                            else -> Icons.Filled.Clear
                                        },
                                        contentDescription = stringResource(id = screen.resourceId),
                                        tint = if (isSelected) MaterialTheme.colors.primary else Color.Gray
                                    )
                                    Text(
                                        stringResource(screen.resourceId),
                                        color = if (isSelected) MaterialTheme.colors.primary else Color.Gray,
                                        modifier = Modifier.padding(top = 5.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.size(20.dp))
                            }
                        }
                        MainNavHost(navController = navController, modifier = Modifier)
                    }
                    WindowWidthSizeClass.Expanded ->
                        Row(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Components.CustomNavigationDrawer(
                                modifier = Modifier.widthIn(max = expandedDrawerSize),
                                listOfItems = listOf(
                                    CustomNavigationDrawer.Home,
                                    CustomNavigationDrawer.Library,
                                    CustomNavigationDrawer.Profile
                                )
                            ) {
                                when (it) {
                                    is CustomNavigationDrawer.Home -> postClick(
                                        Screen.Home,
                                        hapticFeedback,
                                        navController
                                    )
                                    is CustomNavigationDrawer.Library -> postClick(
                                        Screen.Library,
                                        hapticFeedback,
                                        navController
                                    )
                                    is CustomNavigationDrawer.Profile -> postClick(
                                        Screen.Profile,
                                        hapticFeedback,
                                        navController
                                    )
                                }
                            }
                            if (drawerStateOpen)
                                Divider(
                                    color = Color.Gray.copy(alpha = 0.5f), modifier = Modifier
                                        .width(0.6.dp)
                                        .fillMaxHeight()
                                )
                            MainNavHost(navController = navController)
                        }
                    else -> MainNavHost(navController = navController, modifier = Modifier.padding(bottom = compactDrawerSize))
                }
            }
            if (windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact)
                BottomNavigation(modifier = Modifier
                    .height(compactDrawerSize)
                    .align(Alignment.BottomCenter)) {
                    items.forEach { screen ->
                        val isSelected = currentDestination?.route == screen.route
                        BottomNavigationItem(
                            icon = {
                                Icon(
                                    when (screen) {
                                        Screen.Home -> if (isSelected) Icons.Filled.Home else Icons.Default.Home
                                        Screen.Library -> if (isSelected) Icons.Filled.List else Icons.Default.List
                                        Screen.Profile -> if (isSelected) Icons.Filled.Person else Icons.Default.Person
                                        else -> Icons.Filled.Clear
                                    },
                                    contentDescription = stringResource(id = screen.resourceId),
                                    tint = if (isSelected) MaterialTheme.colors.primary else Color.Gray
                                )
                            },
                            label = {
                                Text(
                                    stringResource(screen.resourceId),
                                    color = if (isSelected) MaterialTheme.colors.primary else Color.Gray
                                )
                            },
                            selected = isSelected,
                            onClick = {
                                postClick(screen, hapticFeedback, navController)
                            },
                            modifier = Modifier.background(color = MaterialTheme.colors.background),
                            selectedContentColor = Color.Blue
                        )
                    }
                }
        }
    }

    @Composable
    private fun MainNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
        NavHost(
            navController,
            startDestination = Screen.Main.route,
            modifier = modifier
        ) {
            navigation(startDestination = Screen.Home.route, route = Screen.Main.route) {
                composable(Screen.Home.route) { Home(navController) }
                composable(Screen.Profile.route) { Profile(navController) }
                composable(Screen.Library.route) { Library(navController) }
            }
            composable(Screen.Detail.route) { Detail(navController) }
        }
    }

    @Composable
    private fun Home(navController: NavController) {
        HomeScreen(navController).Main()
    }

    @Composable
    private fun Library(navController: NavController) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text("I'm in the library", modifier = Modifier.align(Alignment.Center))
        }
    }

    @Composable
    private fun Profile(navController: NavController) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text("It's my profile", modifier = Modifier.align(Alignment.Center))
        }
    }

    @Composable
    private fun Detail(navController: NavController) {
        DetailScreen(navController, detailScreenViewModel = detailScreenViewModel).Main()
    }

    private fun postClick(
        screen: Screen,
        hapticFeedback: HapticFeedback,
        navController: NavController
    ) {
        hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
        navController.navigate(screen.route) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }
    }

}