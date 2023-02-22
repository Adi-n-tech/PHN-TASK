package com.app.trackinggadgetspro.Interface

import retrofit2.http.GET
import retrofit2.http.POST
import okhttp3.RequestBody
import retrofit2.http.FormUrlEncoded

interface APIResponseListener {
    fun onSuccess(callResponse: Any?, requestID: Int?)
    fun onFailure(error: Throwable?, requestID: Int?)
}