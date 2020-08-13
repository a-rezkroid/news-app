package com.rezk.newsfeedsapp.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.rezk.newsfeedsapp.store.rootDestinations
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.lang.Exception
import kotlin.reflect.KClass

abstract class BaseFragment<P : BaseViewModel, V : ViewDataBinding>(clazz: KClass<P>) : Fragment() {

    val viewModel by sharedViewModel(clazz)

    private val appBarConfig = AppBarConfiguration.Builder(rootDestinations).build()
    private val disposable = CompositeDisposable()
    var toolbar: Toolbar? = null
    var navController: NavController? = null


    var dataBindingView: V? = null


    lateinit var baseActivity: BaseActivity<*, *>

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    protected abstract fun initFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        initLiveDataObservers()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        baseActivity = context as BaseActivity<*, *>
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBindingView =
            DataBindingUtil.inflate<ViewDataBinding>(inflater, getLayoutId(), container, false) as V
        dataBindingView?.lifecycleOwner = this
        return dataBindingView?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFragment()

    }


    override fun onResume() {
        super.onResume()
        loadData()
    }

    protected open fun loadData() {

    }
    

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }


    fun onBackPressed(): Boolean {
        return navController?.navigateUp(appBarConfig) ?: false
    }

    fun popToRoot(inclusive: Boolean = false) {
        navController?.popBackStack(navController?.graph!!.startDestination, inclusive)
    }

    fun navigateInside(@IdRes destId: Int) {

        try {
            view?.findNavController()?.navigate(destId)
        } catch (e: Exception) {

        }
    }

    fun navigateInside(@IdRes destId: Int, args: Bundle) {
        try {
            view?.findNavController()?.navigate(destId, args)

        } catch (e: Exception) {

        }
    }


    fun addToDisposable(disposable: Disposable) {
        this.disposable.remove(disposable)
        this.disposable.add(disposable)
    }

    open fun initLiveDataObservers() {

    }

    fun showErrorMsg(msg: String) {
        baseActivity.showErrorMsg(msg)
    }


    fun showToasterMsg(msg: String) {
        baseActivity.showToasterMsg(msg)
    }

    fun showLoading() {
        baseActivity.showLoading()
    }

    fun hideLoading() {
        baseActivity.hideLoading()
    }


}