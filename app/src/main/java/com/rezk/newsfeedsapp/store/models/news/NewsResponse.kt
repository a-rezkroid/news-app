package com.rezk.newsfeedsapp.store.models.news

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

data class NewsResponse(
    val articles: List<Article>,
    val sortBy: String,
    val source: String,
    val status: String
)

@Parcelize
data class Article(
    val author: String?,
    val description: String?,
    val publishedAt: String?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
) : Parcelable