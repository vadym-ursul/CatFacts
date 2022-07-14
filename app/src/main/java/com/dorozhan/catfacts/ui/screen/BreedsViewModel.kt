package com.dorozhan.catfacts.ui.screen

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dorozhan.catfacts.data.repository.CatsRepository
import com.dorozhan.catfacts.data.repository.paged.BreedSource
import com.dorozhan.catfacts.model.Breed
import kotlinx.coroutines.flow.Flow

class BreedsViewModel(
    catsRepository: CatsRepository
) : ViewModel() {

    val breeds: Flow<PagingData<Breed>> = Pager(PagingConfig(pageSize = 20)) {
        BreedSource(catsRepository)
    }.flow
}