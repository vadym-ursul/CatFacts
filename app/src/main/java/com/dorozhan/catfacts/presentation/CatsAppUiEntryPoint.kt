package com.dorozhan.catfacts.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import com.dorozhan.catfacts.presentation.flow.NavGraphs
import com.dorozhan.catfacts.presentation.flow.destinations.OnBoardingScreenDestination
import com.dorozhan.catfacts.presentation.flow.splash.SplashViewModel
import com.ramcosta.composedestinations.DestinationsNavHost

@Composable
fun CatsAppUiEntryPoint(mainViewModel: SplashViewModel) {
//    val mainViewModel = hiltViewModel<MainViewModel>()
    val onboardPassed = mainViewModel.onboardPassedLiveData.observeAsState(false).value
    val startRoute = if (onboardPassed) {
        NavGraphs.root.startRoute
    } else OnBoardingScreenDestination

    DestinationsNavHost(
        navGraph = NavGraphs.root,
        startRoute = startRoute
    )
}