package com.sergio.sergiodevhub.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.core.view.WindowCompat
import com.sergio.sergiodevhub.data.repository.MovieRepositoryImpl
import com.sergio.sergiodevhub.domain.usecase.GetPopularMoviesUseCase
import com.sergio.sergiodevhub.presentation.ui.MovieListScreen
import com.sergio.sergiodevhub.presentation.ui.theme.SergioDevHubTheme
import com.sergio.sergiodevhub.presentation.viewmodel.MovieViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = false

        setContent {
            val viewModel = remember {
                val repository = MovieRepositoryImpl()
                val useCase = GetPopularMoviesUseCase(repository)
                MovieViewModel(useCase)
            }

            MovieListScreen(movieViewModel = viewModel)
        }

    }
}