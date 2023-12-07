package com.example.leafywalls

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.leafywalls.presentation.Screen
import com.example.leafywalls.presentation.home_screen.HomeScreen
import com.example.leafywalls.presentation.photo_details.PhotoDetailScreen
import com.example.leafywalls.presentation.photo_list.ExploreList
import com.example.leafywalls.ui.theme.LeafyWallsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LeafyWallsTheme {
                
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    
                    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
                        
                        composable(route = Screen.HomeScreen.route) {
                            HomeScreen(navController = navController)
                        }

//                        composable(route = Screen.ExploreList.route) {
//                            ExploreList(navController = navController)
//                        }

                        composable(route = Screen.PhotoDetailScreen.route + "/{photoId}") {
                            PhotoDetailScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}

