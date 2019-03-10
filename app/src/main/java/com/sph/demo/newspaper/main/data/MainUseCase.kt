package com.sph.demo.newspaper.main.data

import android.content.Context
import com.sph.demo.newspaper.common.util.DefaultSubscriber
import com.sph.demo.newspaper.common.util.UseCase
import com.sph.demo.newspaper.main.data.entity.RecordEntity


class MainUseCase(context: Context) : UseCase() {

    private var repository: MainRepository = MainRepository(context)

    fun getRecordsCount (subscriber:DefaultSubscriber<Int>){
        execute(repository.getResponseCount(),subscriber)
    }

    fun getAllRecordsFromNetwork(start:Int, end:Int,subscriber: DefaultSubscriber<List<RecordEntity>>) {
        execute(repository.loadAllReports()
                .concatMap { _: Boolean -> repository.selectAllRecords(start,end)}, subscriber)
    }

    fun getAllRecordsFromDatabase (start:Int, end:Int,subscriber: DefaultSubscriber<List<RecordEntity>>) {
        execute(repository.selectAllRecords(start,end),subscriber)
    }
}