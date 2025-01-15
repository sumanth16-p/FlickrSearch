package com.flickrsearch

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.flickrsearch.network.FlickrSearchApiService
import com.flickrsearch.repo.FlickrSearchRepoImpl
import com.flickrsearch.repo.FlickrSearchUseCase
import com.flickrsearch.theme.FlickrSearchTheme
import com.flickrsearch.ui.FlickrSearchScreen
import com.flickrsearch.ui.FlickrViewModel
import com.flickrsearch.ui.ResultScreen

class FlickrSearchActivity : ComponentActivity() {
    @SuppressLint("StateFlowValueCalledInComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = FlickrViewModel(FlickrSearchUseCase(FlickrSearchRepoImpl(FlickrSearchApiService.create())))

        setContent() {
            FlickrSearchTheme(darkTheme = false){
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "search") {
                    composable("search") {
                        FlickrSearchScreen(viewModel = viewModel, navController = navController)
                    }
                    composable("detail/{index}") { backStackEntry ->
                        val index = backStackEntry.arguments?.getString("index")?.toIntOrNull()
                        val photo = viewModel.photos.value[index ?: 0]
                        ResultScreen(photo = photo, navController = navController)
                    }
                }
            }
        }
    }
}