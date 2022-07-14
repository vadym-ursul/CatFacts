package com.dorozhan.catfacts.di

import com.dorozhan.catfacts.ui.screen.BreedsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { BreedsViewModel(get()) }
}