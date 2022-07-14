package com.dorozhan.catfacts.ui.screen

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.dorozhan.catfacts.R

@Composable
fun FactScreen(breedsViewModel: BreedsViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.facts)) }
            )
        },
        content = {
            Text(text = "FactScreen")
        }
    )
}