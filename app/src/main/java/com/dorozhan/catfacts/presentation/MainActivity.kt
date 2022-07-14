package com.dorozhan.catfacts.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.dorozhan.catfacts.presentation.screen.MainScreen
import com.dorozhan.catfacts.presentation.screen.MainViewModel
import com.dorozhan.catfacts.presentation.theme.CatFactsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CatFactsTheme {
                MainScreen(mainViewModel)
            }
        }
    }
}