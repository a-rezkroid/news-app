package com.rezk.newsfeedsapp.ui.news.fragments

import android.os.Bundle
import androidx.lifecycle.Observer
import com.rezk.newsfeedsapp.R
import com.rezk.newsfeedsapp.base.BaseFragment
import com.rezk.newsfeedsapp.databinding.FragmentNewsBinding
import com.rezk.newsfeedsapp.store.ARTICLE_MODEL_KEY
import com.rezk.newsfeedsapp.store.remote.NetworkConstants
import com.rezk.newsfeedsapp.store.remote.NetworkConstants.FIRST_SOURCE
import com.rezk.newsfeedsapp.store.remote.NetworkConstants.Second_SOURCE
import com.rezk.newsfeedsapp.ui.news.NewsViewModel
import com.rezk.newsfeedsapp.ui.news.adapter.NewsAdapter
import kotlinx.android.synthetic.main.activity_news.*

class NewsFragment : BaseFragment<NewsViewModel, FragmentNewsBinding>(NewsViewModel::class) {

    private val adapter by lazy {
        NewsAdapter()
    }
    private val toolBar by lazy {
        requireActivity().toolBar
    }

    override fun getLayoutId(): Int = R.layout.fragment_news

    override fun initFragment() {
        toolBar?.title = getString(R.string.label_link_dev)

        dataBindingView?.recyclerView?.adapter = adapter

        adapter.setOnItemClickListener { item, position ->
            navigateInside(R.id.action_des_newsF_to_dest_newsDetailsF, Bundle().apply {
                putParcelable(ARTICLE_MODEL_KEY, item)
            })
        }
    }

    override fun loadData() {
        if (viewModel.newsLiveData.value == null) {
            viewModel.getNews(FIRST_SOURCE, Second_SOURCE)
        }
    }

    override fun initLiveDataObservers() {
        super.initLiveDataObservers()
        viewModel.newsLiveData.observe(this, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
    }

}