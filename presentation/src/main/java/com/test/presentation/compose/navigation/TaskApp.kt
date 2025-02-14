package com.test.presentation.compose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.test.presentation.compose.screens.favt_screen.FavtScreen
import com.test.presentation.compose.screens.photos_list.PhotosListScreen

@Composable
fun TaskApp(
    startDest: Routes = Routes.PhotosScreen
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = startDest,
    ) {
        composable<Routes.PhotosScreen> {
            PhotosListScreen(
                moveToFavtScreen = {
                    navController.navigate(Routes.FavtScreen)
                }
            )
        }
        composable<Routes.FavtScreen> {
            FavtScreen()
        }
    }
}