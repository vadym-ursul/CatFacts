package com.dorozhan.catfacts.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.dorozhan.catfacts.presentation.flow.NavGraphs
import com.dorozhan.catfacts.presentation.flow.destinations.OnBoardingScreenDestination
import com.dorozhan.catfacts.presentation.flow.splash.SplashViewModel
import com.ramcosta.composedestinations.DestinationsNavHost

@Composable
fun CatsAppUiEntryPoint(splashViewModel: SplashViewModel) {
    val onboardPassed = splashViewModel.onboardPassedFlow.collectAsState()

    onboardPassed.value?.let {
        val startRoute = if (it) {
            NavGraphs.root.startRoute
        } else OnBoardingScreenDestination

        DestinationsNavHost(
            navGraph = NavGraphs.root,
            startRoute = startRoute
        )

        splashViewModel.setLoading(false)
    }
}