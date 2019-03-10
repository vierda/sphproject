package com.sph.demo.newspaper.main.data

import android.content.Context
import com.sph.demo.newspaper.NewspaperApplication
import com.sph.demo.newspaper.common.data.model.ResponseReport
import com.sph.demo.newspaper.main.data.entity.RecordEntity
import io.reactivex.Observable

class MainRepository(val context: Context) {

    private var databaseRepository = MainDatabaseRepository(NewspaperApplication.getInstance().getDatabase()!!)
    private var networkRepository = MainNetworkRepository(NewspaperApplication.getInstance().getWebService()!!)

    fun getResponseCount(): Observable<Int> {
        return databaseRepository.getResourceCount()
    }

    fun loadAllReports(): Observable<Boolean> {
        return networkRepository.loadAllReport().concatMap { t: ResponseReport ->
            databaseRepository.saveReport(t)
        }
    }


    fun selectAllRecords(start: Int, end: Int): Observable<List<RecordEntity>> {
        return databaseRepository.selectAllRecords(start, end)
    }
}