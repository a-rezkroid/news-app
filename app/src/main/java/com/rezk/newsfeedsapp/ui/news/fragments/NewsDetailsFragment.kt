package com.rezk.newsfeedsapp.ui.news.fragments

import android.content.Intent
import android.net.Uri
import com.rezk.newsfeedsapp.R
import com.rezk.newsfeedsapp.base.BaseFragment
import com.rezk.newsfeedsapp.databinding.FragmentNewsDetailsBinding
import com.rezk.newsfeedsapp.ui.news.NewsViewModel
import kotlinx.android.synthetic.main.activity_news.*


class NewsDetailsFragment :
    BaseFragment<NewsViewModel, FragmentNewsDetailsBinding>(NewsViewModel::class) {

    var websiteUrl: String? = null
    private val toolBar by lazy {
        requireActivity().toolBar
    }

    override fun getLayoutId(): Int = R.layout.fragment_news_details

    override fun initFragment() {
        toolBar?.title = getString(R.string.label_link_dev)
        NewsDetailsFragmentArgs.fromBundle(requireArguments()).let {
            it.articleModel?.let {
                dataBindingView?.model = it
                websiteUrl = it.url ?: ""
            }
        }
        dataBindingView?.openWebsite?.setOnClickListener {
            websiteUrl?.let {
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(it)
                startActivity(i)
            } ?: kotlin.run {
                showErrorMsg("Invalid Url")
            }

        }
    }

}