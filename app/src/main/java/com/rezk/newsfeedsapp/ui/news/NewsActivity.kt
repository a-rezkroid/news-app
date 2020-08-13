package com.rezk.newsfeedsapp.ui.news

import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.rezk.newsfeedsapp.R
import com.rezk.newsfeedsapp.base.BaseActivity
import com.rezk.newsfeedsapp.databinding.ActivityNewsBinding
import com.rezk.newsfeedsapp.store.remote.NetworkConstants
import com.rezk.newsfeedsapp.store.rootDestinations
import kotlinx.android.synthetic.main.activity_news.*
import kotlinx.android.synthetic.main.layout_side_menu.view.*

class NewsActivity : BaseActivity<NewsViewModel, ActivityNewsBinding>(NewsViewModel::class) {

    private lateinit var navigationView: NavigationView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navController: NavController
    private lateinit var toolBar: Toolbar


    override fun getLayoutId(): Int = R.layout.activity_news

    override fun initActivity() {
        initNavigation()
        initSideMenuActions()
    }

    private fun initNavigation() {
        navigationView = dataBindingView.navigationView
        drawerLayout = dataBindingView.drawerLayout
        toolBar = dataBindingView.toolBar
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        val appBarConfig = AppBarConfiguration(rootDestinations, drawerLayout)
        toolBar.setupWithNavController(navController, appBarConfig)
        navigationView.setupWithNavController(navController)
    }

    private fun initSideMenuActions() {
        findViewById<ConstraintLayout>(R.id.exploreItem).setOnClickListener {
            showToasterMsg(it.exploreText.text.toString())
        }
        findViewById<ConstraintLayout>(R.id.galleryItem).setOnClickListener {
            showToasterMsg(it.galleryText.text.toString())

        }
        findViewById<ConstraintLayout>(R.id.liveChatItem).setOnClickListener {
            showToasterMsg(it.liveChatText.text.toString())

        }
        findViewById<ConstraintLayout>(R.id.magazineItem).setOnClickListener {
            showToasterMsg(it.magazineText.text.toString())

        }
        findViewById<ConstraintLayout>(R.id.wishListItem).setOnClickListener {
            showToasterMsg(it.whishListText.text.toString())

        }
    }

    override fun initLiveDataObservers() {
        super.initLiveDataObservers()
        networkLiveEvent.observe(this, Observer {
            it?.let { state ->
                if (state && viewModel.newsLiveData.value == null) {
                    viewModel.getNews(NetworkConstants.FIRST_SOURCE, NetworkConstants.Second_SOURCE)
                }
            }
        })
    }


}