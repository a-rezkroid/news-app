package com.rezk.newsfeedsapp.base

import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.widget.FrameLayout
import android.widget.TextView

import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import com.rezk.newsfeedsapp.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import kotlin.reflect.KClass

abstract class BaseActivity<P : BaseViewModel, V : ViewDataBinding>(clazz: KClass<P>) :
    AppCompatActivity() {


    protected val viewModel: P by viewModel(clazz)

    val dataBindingView by lazy {
        DataBindingUtil.setContentView(this, getLayoutId()) as V
    }

    private var progressDialog: Dialog? = null
    private val disposable = CompositeDisposable()

    val networkLiveEvent = MutableLiveData<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBindingView.lifecycleOwner = this
        initActivity()
        initDialog()
        initLiveDataObservers()

        checkInternetConnection()
    }


    private fun initDialog() {
        progressDialog = Dialog(this,R.style.Theme_Dialog)
        progressDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        progressDialog?.setCancelable(false)
        progressDialog?.setCanceledOnTouchOutside(false)
        progressDialog?.setContentView(R.layout.loader_dialog)
        progressDialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

    }


    protected abstract fun initActivity()

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    open fun initLiveDataObservers() {
        viewModel.let {
            it.errorMsgLiveData.observe(this, Observer { msg ->
                msg?.let {
                    showErrorMsg(msg)
                }

            })

            it.loadingLiveData.observe(this, Observer { loading ->
                if (loading)
                    progressDialog?.show()
                else
                    progressDialog?.dismiss()

            })
        }

    }


    fun showToasterMsg(msg: String) {
        runOnUiThread { Toast.makeText(this, msg, Toast.LENGTH_SHORT).show() }
    }

    fun showErrorMsg(msg: String) {

        runOnUiThread {
            val toast = Toast(this)
            val view = layoutInflater.inflate(R.layout.layout_red_toast, FrameLayout(this))
            toast.duration = Toast.LENGTH_SHORT
            toast.setGravity(Gravity.BOTTOM, 10, 300);
            (view.findViewById<TextView>(R.id.message)).text = msg
            toast.view = view
            toast.show()
        }
    }

    fun addToDisposable(disposable: Disposable) {
        this.disposable.remove(disposable)
        this.disposable.add(disposable)
    }

    fun checkInternetConnection() {
        addToDisposable(
            ReactiveNetwork.observeNetworkConnectivity(this)
                .skip(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { connectivity ->
                        networkLiveEvent.value = connectivity.available()
                    },
                    Timber::e
                )
        )
    }

    override fun onDestroy() {
        disposable.clear()
        super.onDestroy()
    }

    fun showLoading() {
        progressDialog?.show()
    }

    fun hideLoading() {
        progressDialog?.dismiss()
    }
}