package com.sph.demo.newspaper.main.presentation.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.sph.demo.newspaper.common.util.DefaultSubscriber
import com.sph.demo.newspaper.main.data.MainUseCase
import com.sph.demo.newspaper.main.data.entity.RecordEntity
import com.sph.demo.newspaper.main.presentation.view.MainDataView

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG = MainViewModel::class.java.simpleName
    private val START = 2008
    private val END = 2018

    private lateinit var dataView: MainDataView
    private var useCase: MainUseCase = MainUseCase(application.baseContext)

    private var loadRecordsLiveData: MutableLiveData<List<RecordEntity>> = MutableLiveData()
    private var countLiveData: MutableLiveData<Int> = MutableLiveData()


    fun getLoadRecordsLiveData(): MutableLiveData<List<RecordEntity>> {
        return loadRecordsLiveData
    }

    fun getCountLiveData(): MutableLiveData<Int> {
        return countLiveData
    }


    fun setDataView(dataView: MainDataView) {
        this.dataView = dataView
    }

    fun getResourceCount() {
        useCase.getRecordsCount(object : DefaultSubscriber<Int>(){
            override fun onError(t: Throwable?) {
                Log.e(TAG,t?.message)
            }

            override fun onNext(t: Int) {
               countLiveData.postValue(t)

            }
        })
    }

    fun getAllRecords(total:Int) {
        if (total>0) {
            useCase.getAllRecordsFromDatabase(START,END,RecordSubcriber())
        } else {
            useCase.getAllRecordsFromNetwork(START,END,RecordSubcriber())
        }

    }


    inner class RecordSubcriber : DefaultSubscriber<List<RecordEntity>>() {
        override fun onError(t: Throwable?) {
            Log.e(TAG, ""+t?.printStackTrace())

        }

        override fun onNext(t: List<RecordEntity>) {
            loadRecordsLiveData.postValue(t)
        }
    }

}