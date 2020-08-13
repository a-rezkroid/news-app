package com.rezk.newsfeedsapp.ui.news

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.rezk.newsfeedsapp.base.BaseViewModel
import com.rezk.newsfeedsapp.store.models.news.Article
import com.rezk.newsfeedsapp.store.models.news.NewsResponse
import com.rezk.newsfeedsapp.store.remote.NetworkConstants
import com.rezk.newsfeedsapp.store.repos.NewsRepo
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.zipWith
import io.reactivex.schedulers.Schedulers

class NewsViewModel(private val newsRepo: NewsRepo) : BaseViewModel() {

    val newsLiveData = MutableLiveData<List<Article>>()

    fun getNews(firstSource: String, secondSource: String) {
        addToDisposable(

            newsRepo.getNewsFromSource(firstSource)
                .zipWith(newsRepo.getNewsFromSource(secondSource)) { firstList, secondList ->
                    val result = mutableListOf<Article>()
                    result.addAll(firstList)
                    result.addAll(secondList)
                    result
                }
                .doOnSubscribe {
                    loadingLiveData.postValue(true)
                }.doFinally {
                    loadingLiveData.postValue(false)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    newsLiveData.value = it
                }, { error ->
                    processError(error)
                })
        )
    }

}