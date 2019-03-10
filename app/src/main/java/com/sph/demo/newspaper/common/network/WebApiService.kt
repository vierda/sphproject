package com.sph.demo.newspaper.common.network

import com.sph.demo.newspaper.common.data.model.ResponseReport
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface WebApiService {

    companion object {

        private const val protocol = "https"
        private const val serverName = "data.gov.sg"
        private const val resourceId = "a807b7ab-6cad-4aa6-87d0-e283a7353a0f"


        fun getHost(): String {
            val builder = StringBuilder(1024)

            builder.append(protocol)
                    .append("://")
                    .append(serverName)

            return builder.toString()
        }

        fun create(): WebApiService {
            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(getHost())
                    .build()

            return retrofit.create(WebApiService::class.java)
        }
    }

    @GET("/api/action/datastore_search?resource_id=${resourceId}")
    fun loadAllReport(): Call<ResponseReport>

}