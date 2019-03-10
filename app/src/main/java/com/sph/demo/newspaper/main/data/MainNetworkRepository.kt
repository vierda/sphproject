package com.sph.demo.newspaper.main.data

import com.sph.demo.newspaper.common.data.model.ResponseReport
import com.sph.demo.newspaper.common.network.WebApiService
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Callback

open class MainNetworkRepository(val webApiService: WebApiService) {

    open fun loadAllReport(): Observable<ResponseReport> {
        return Observable.create {
            webApiService.loadAllReport().enqueue(object : Callback<ResponseReport> {
                override fun onFailure(call: Call<ResponseReport>?, t: Throwable?) {
                    it.onError(Throwable(t?.message))
                    it.onComplete()

                }

                override fun onResponse(call: Call<ResponseReport>, response: retrofit2.Response<ResponseReport>) {
                    it.onNext(response.body()!!)
                    it.onComplete()
                }
            })
        }
    }

}