package com.dorozhan.catfacts.presentation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.dorozhan.catfacts.presentation.screen.NavGraphs
import com.dorozhan.catfacts.presentation.screen.destinations.OnBoardingScreenDestination
import com.ramcosta.composedestinations.DestinationsNavHost

@Composable
fun CatsAppUiEntryPoint() {
    val mainViewModel = hiltViewModel<MainViewModel>()
    val startRoute =
        if (mainViewModel.isFirstTimeLaunch) {
            mainViewModel.isFirstTimeLaunch = false
            OnBoardingScreenDestination
        } else NavGraphs.root.startRoute

    DestinationsNavHost(
        navGraph = NavGraphs.root,
        startRoute = startRoute)
}