package com.dorozhan.catfacts.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.dorozhan.catfacts.ui.screen.BreedsViewModel
import com.dorozhan.catfacts.ui.screen.MainScreen
import com.dorozhan.catfacts.ui.theme.AppTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val breedsViewModel: BreedsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                MainScreen(breedsViewModel)
            }
        }
    }
}