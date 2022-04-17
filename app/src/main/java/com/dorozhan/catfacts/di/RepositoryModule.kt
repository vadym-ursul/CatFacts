package com.dorozhan.catfacts.di

import com.dorozhan.catfacts.data.network.retrofit.Api
import com.dorozhan.catfacts.data.network.ktor.Api as KtorApi
import com.dorozhan.catfacts.data.repository.CatsRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { createRepository(get(), get()) }
}

fun createRepository(
    api: Api,
    ktorApi: KtorApi
): CatsRepository = CatsRepository(api, ktorApi)