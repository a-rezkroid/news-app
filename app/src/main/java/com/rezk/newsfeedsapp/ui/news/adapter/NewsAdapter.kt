package com.rezk.newsfeedsapp.ui.news.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.rezk.newsfeedsapp.base.BaseAdapter
import com.rezk.newsfeedsapp.base.BaseBindingViewHolder
import com.rezk.newsfeedsapp.databinding.NewsItemBinding
import com.rezk.newsfeedsapp.store.models.news.Article

class NewsAdapter : BaseAdapter<Article, ViewHolder>() {

    override fun getViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(NewsItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }
}

class ViewHolder(private val binding: NewsItemBinding) : BaseBindingViewHolder<Article>(binding) {
    override fun bindItem(item: Article?, position: Int) {
        item?.let {
            binding.model = it
        }
    }

}