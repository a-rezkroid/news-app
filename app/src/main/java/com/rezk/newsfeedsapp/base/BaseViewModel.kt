package com.rezk.newsfeedsapp.base

import androidx.lifecycle.ViewModel
import com.hadilq.liveevent.LiveEvent
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.io.IOException
import org.json.JSONObject
import java.lang.Exception
import timber.log.Timber


open class BaseViewModel : ViewModel() {

    val errorMsgLiveData: LiveEvent<String> = LiveEvent()
    val successMsgLiveData: LiveEvent<String> = LiveEvent()
    val loadingLiveData: LiveEvent<Boolean> = LiveEvent()
    val unAuthorizedUserLiveData: LiveEvent<Boolean> = LiveEvent()
    val finishScreenLiveData: LiveEvent<Boolean> = LiveEvent()
    private val disposable: CompositeDisposable = CompositeDisposable()


    fun addToDisposable(disposable: Disposable) {
        this.disposable.remove(disposable)
        this.disposable.add(disposable)

    }

    fun processError(throwable: Throwable) {
        loadingLiveData.value = false
        when (throwable) {
            is HttpException -> {
                errorMsgLiveData.value = "An error occur try again later."

            }

            is IOException -> {
                errorMsgLiveData.value = "please check your connection."
            }

            else -> {
                errorMsgLiveData.value = "An error occur try again later."
            }

        }

    }


    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }
}