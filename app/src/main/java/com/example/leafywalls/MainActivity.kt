package com.example.leafywalls

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.leafywalls.presentation.Screen
import com.example.leafywalls.presentation.favorites_screen.FavouriteScreen
import com.example.leafywalls.presentation.home_screen.HomeScreen
import com.example.leafywalls.presentation.login_screen.LoginScreen
import com.example.leafywalls.presentation.photo_details.PhotoDetailScreen
import com.example.leafywalls.presentation.random_photo.RandomScreen
import com.example.leafywalls.presentation.search_screen.SearchScreen
import com.example.leafywalls.ui.theme.LeafyWallsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            LeafyWallsTheme {
                val localFocusManager = LocalFocusManager.current
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(Unit) {
                            detectTapGestures(onTap = {
                                localFocusManager.clearFocus()
                            })
                        },
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    
                    NavHost(navController = navController, startDestination = Screen.WelcomeScreen.route) {
                        
                        composable(route = Screen.HomeScreen.route) {
                            HomeScreen(navController = navController)
                        }

                        composable(route = Screen.WelcomeScreen.route) {
                            LoginScreen()
                        }

                        composable(route = Screen.RandomScreen.route) {
                            RandomScreen(navController = navController)
                        }

                        composable(
                            route = Screen.PhotoDetailScreen.route + "/{photoId}",
                            enterTransition = {
                                slideIntoContainer(
                                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                                    animationSpec = tween(500)
                                ) + fadeIn(tween(300, 200))
                            },
                            exitTransition = {
                                slideOutOfContainer(
                                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                                    animationSpec = tween(500)
                                ) + fadeOut(tween(250, 100))
                            },
                        ) {
                            PhotoDetailScreen(navController = navController)
                        }


                        composable(
                            route = Screen.SearchScreen.route,
                            exitTransition = {
                                fadeOut(animationSpec = tween(250))
                            },
                            popEnterTransition = {
                                fadeIn(animationSpec = tween(250))
                            }
                        ) {
                            SearchScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}

