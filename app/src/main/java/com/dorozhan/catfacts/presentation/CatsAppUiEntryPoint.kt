package com.dorozhan.catfacts.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import com.dorozhan.catfacts.presentation.flow.NavGraphs
import com.dorozhan.catfacts.presentation.flow.destinations.OnBoardingScreenDestination
import com.dorozhan.catfacts.presentation.flow.splash.SplashViewModel
import com.ramcosta.composedestinations.DestinationsNavHost

@Composable
fun CatsAppUiEntryPoint(mainViewModel: SplashViewModel) {
    val onboardPassed = mainViewModel.onboardPassedLiveData.observeAsState(null).value
    onboardPassed?.let {
        val startRoute = if (it) {
            NavGraphs.root.startRoute
        } else OnBoardingScreenDestination

        DestinationsNavHost(
            navGraph = NavGraphs.root,
            startRoute = startRoute
        )
    }
}