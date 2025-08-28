package com.sergio.sergiodevhub.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.sergio.sergiodevhub.domain.model.Movie
import com.sergio.sergiodevhub.presentation.viewmodel.MovieViewModel
import com.sergio.sergiodevhub.presentation.ui.components.SearchBar
import com.sergio.sergiodevhub.presentation.ui.components.MoviePagingGrid
import com.sergio.sergiodevhub.presentation.ui.components.GenericEmptyState
import com.sergio.sergiodevhub.presentation.ui.components.GenericErrorState

@Composable
fun MovieListScreen(
    onMovieClick: (Int) -> Unit = {},
    movieViewModel: MovieViewModel = hiltViewModel()
) {
    val movies = movieViewModel.pagedMovies.collectAsLazyPagingItems()
    var searchQuery by remember { mutableStateOf("") }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // Header
            Text(
                text = "Most Popular Movies",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Search Bar
            SearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                onClearClick = { searchQuery = "" }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Content
            Box(modifier = Modifier.fillMaxSize()) {
                when (movies.loadState.refresh) {
                    is LoadState.Loading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                    is LoadState.Error -> {
                        val error = movies.loadState.refresh as LoadState.Error
                        GenericErrorState(
                            message = error.error.localizedMessage ?: "Unknown error",
                            onRetry = { movies.retry() },
                            modifier = Modifier.align(Alignment.Center),
                            title = "Error loading movies"
                        )
                    }
                    else -> {
                        if (movies.itemCount == 0) {
                            GenericEmptyState(
                                title = "No movies found",
                                description = "It looks like there are no movies to display right now. Please try again.",
                                actionText = "Try Again",
                                onAction = { movies.retry() },
                                modifier = Modifier.align(Alignment.Center)
                            )
                        } else {
                            MoviePagingGrid(
                                movies = movies,
                                searchQuery = searchQuery,
                                onMovieClick = onMovieClick,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
            }
        }
    }
}














