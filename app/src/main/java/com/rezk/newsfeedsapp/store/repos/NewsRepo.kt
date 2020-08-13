package com.rezk.newsfeedsapp.store.repos

import com.rezk.newsfeedsapp.store.models.news.Article
import com.rezk.newsfeedsapp.store.models.news.NewsResponse
import com.rezk.newsfeedsapp.store.remote.NetworkConstants
import com.rezk.newsfeedsapp.store.remote.NetworkManager
import io.reactivex.Observable

class NewsRepo(private val networkManager: NetworkManager) {

    fun getNewsFromSource(source: String): Observable<List<Article>> {
        val params = hashMapOf<String, Any>()
        params["source"] = source
        params["apiKey"] = NetworkConstants.API_KEY

        return networkManager.getRequest(
            NetworkConstants.ARTICLES_URL,
            HashMap(), params, NewsResponse::class.java
        ).map {
            return@map it.articles
        }
    }

}