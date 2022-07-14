package com.dorozhan.catfacts.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dorozhan.catfacts.data.repository.CatsRepository
import com.dorozhan.catfacts.data.repository.paged.BreedSource
import com.dorozhan.catfacts.domain.model.Breed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    catsRepository: CatsRepository
) : ViewModel() {

    val breeds: Flow<PagingData<Breed>> = Pager(PagingConfig(pageSize = 20)) {
        BreedSource(catsRepository)
    }.flow
}