package com.dorozhan.catfacts.presentation

import androidx.compose.runtime.Composable
import com.dorozhan.catfacts.presentation.screen.NavGraphs
import com.ramcosta.composedestinations.DestinationsNavHost

@Composable
fun CatsAppUiEntryPoint() {
    DestinationsNavHost(navGraph = NavGraphs.root)
}