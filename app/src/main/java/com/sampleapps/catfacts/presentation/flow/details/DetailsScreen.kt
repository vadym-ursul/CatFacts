package com.sampleapps.catfacts.presentation.flow.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sampleapps.catfacts.R
import com.sampleapps.catfacts.presentation.library.DetailsAppBar
import com.sampleapps.catfacts.presentation.util.setSystemBarsColor

@OptIn(ExperimentalMaterial3Api::class)
@Destination(navArgsDelegate = DetailsNavArgs::class)
@Composable
fun CatDetailsScreen(
    detailsViewModel: DetailsViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
) {
    val breedState = detailsViewModel.breed.collectAsState()

    setSystemBarsColor(Color.Black, darkIcons = false)

    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding(),
        topBar = {
            DetailsAppBar(
                text = breedState.value?.title.orEmpty(),
                isFavoriteChecked = breedState.value?.favorite,
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color.Black,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                ),
                onBackClick = { navigator.navigateUp() },
                onFavoriteClick = {
                    detailsViewModel.onFavoriteClick(breedState.value, it)
                })
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = breedState.value?.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Fit,
                    fallback = null,
                    error = painterResource(id = R.drawable.ic_cat_placeholder_512),
                )
            }
        }
    )
}