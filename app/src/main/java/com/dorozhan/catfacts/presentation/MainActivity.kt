package com.dorozhan.catfacts.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.dorozhan.catfacts.presentation.flow.splash.SplashViewModel
import com.dorozhan.catfacts.presentation.theme.CatFactsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel by viewModels<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
            .setKeepOnScreenCondition {
                mainViewModel.isLoading.value == true
            }
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            CatFactsTheme {
                CatsAppUiEntryPoint(mainViewModel)
            }
        }
    }
}