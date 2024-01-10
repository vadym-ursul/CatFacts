package com.sampleapps.catfacts.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun setSystemBarsColor(
    statusBarColor: Color,
    navigationBarColor: Color = statusBarColor,
    darkIcons: Boolean
) {
    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.apply {
            setStatusBarColor(
                color = statusBarColor,
                darkIcons = darkIcons
            )
            setNavigationBarColor(
                color = navigationBarColor,
                darkIcons = darkIcons
            )
        }
    }
}