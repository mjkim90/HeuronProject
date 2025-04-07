package com.heuron.heuronproject.core

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.heuron.heuronproject.presentation.imagedetail.ImageDetailScreen
import com.heuron.heuronproject.presentation.imagelist.ImageListScreen

@Composable
fun HeuronAppNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            ImageListScreen(navController = navController)
        }
        composable(
            route = "detail/{imageId}/{imageUrl}",
            arguments = listOf(
                navArgument("imageId") { type = NavType.StringType },
                navArgument("imageUrl") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val imageId = backStackEntry.arguments?.getString("imageId") ?: ""
            val imageUrl = backStackEntry.arguments?.getString("imageUrl") ?: ""

            ImageDetailScreen(
                imageId = imageId,
                imageUrl = imageUrl,
                viewModel = hiltViewModel(),
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}