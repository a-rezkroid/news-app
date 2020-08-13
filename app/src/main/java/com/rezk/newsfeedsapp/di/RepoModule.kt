package com.rezk.newsfeedsapp.di

import com.rezk.newsfeedsapp.store.repos.NewsRepo
import org.koin.dsl.module


val repoModule = module {

    single {
        NewsRepo(get ())
    }

}

