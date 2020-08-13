package com.rezk.newsfeedsapp.di

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.rezk.newsfeedsapp.store.remote.APIService
import com.rezk.newsfeedsapp.store.remote.NetworkConstants
import com.rezk.newsfeedsapp.store.remote.NetworkManager
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val networkModule = module {

    single {
        OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .callTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    single {
        Retrofit.Builder()
            .client(get())
            .baseUrl(NetworkConstants.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.newThread()))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(APIService::class.java)
    }

    single {
        NetworkManager(get())
    }

}

