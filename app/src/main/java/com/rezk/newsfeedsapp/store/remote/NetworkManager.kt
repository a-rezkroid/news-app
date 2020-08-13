package com.rezk.newsfeedsapp.store.remote

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.util.HashMap
import io.reactivex.Completable
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody

class NetworkManager(private val apiService: APIService) {
    private val headers: HashMap<String, String> = HashMap()
    private val gson = GsonBuilder().serializeNulls().create()

    init {
        headers["Accept"] = "application/json"
        headers["Content-Type"] = "application/json"
    }

    fun <V> getRequest(api: String, headers: HashMap<String, String> = HashMap(), params: HashMap<String, Any> = HashMap(), parseClass: Class<V>): Observable<V> {
        this.headers.putAll(headers)
        return apiService.getRequest(api, this.headers, params).map {
            gson.fromJson(it, parseClass)
        }
    }

    fun <V> postRequest(api: String, headers: HashMap<String, String> = HashMap(), params: HashMap<String, Any> = HashMap(), parseClass: Class<V>): Observable<V> {
        this.headers.putAll(headers)
        return apiService.postRequest(api, this.headers, params).map {
            gson.fromJson(it, parseClass)
        }
    }

    fun postRequest(api: String, headers: HashMap<String, String> = HashMap(), params: HashMap<String, Any> = HashMap()): Completable {
        this.headers.putAll(headers)
        return apiService.postRequestCompletable(api, this.headers, params)
    }


}
