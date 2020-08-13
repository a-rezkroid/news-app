package com.rezk.newsfeedsapp.di

import com.rezk.newsfeedsapp.ui.news.NewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {

    viewModel {
        NewsViewModel(get())
    }
}