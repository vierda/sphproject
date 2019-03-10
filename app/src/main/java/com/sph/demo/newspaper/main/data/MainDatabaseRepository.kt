package com.sph.demo.newspaper.main.data

import com.sph.demo.newspaper.common.data.database.AppDatabase
import com.sph.demo.newspaper.common.data.model.Record
import com.sph.demo.newspaper.common.data.model.ResponseReport
import com.sph.demo.newspaper.main.data.entity.RecordEntity
import io.reactivex.Observable
import java.util.concurrent.Executors

open class MainDatabaseRepository (val database : AppDatabase) {

    open fun saveReport(report: ResponseReport): Observable<Boolean> {
        return Observable.create<Boolean> {
            Executors.newSingleThreadExecutor().execute(Runnable {
                database.ResponseReportDao().insert(report)
                if (report.result.records.isNotEmpty()) {
                    database.RecordDao().insertAll(report.result.records)
                }
                it.onNext(true)
            })
        }
    }


    open fun getResourceCount(): Observable<Int> {
        return Observable.create {
            Executors.newSingleThreadExecutor().execute(Runnable {
                val count = database.ResponseReportDao().getResponseCount()
                it.onNext(count)
            })
        }
    }


    open fun selectAllRecords(start: Int, end: Int): Observable<List<RecordEntity>> {
        return Observable.create {

            val entities = ArrayList<RecordEntity>()

            Executors.newSingleThreadExecutor().execute(Runnable {
                for (year in start..end) {

                    val yearQuarter = "%${year}%"
                    val records: List<Record> = database.RecordDao().selectRecordsByYear(yearQuarter)
                    records.sortedWith(compareBy({ it.id }))

                    var total = 0.0

                    records.forEach { record: Record ->
                        total += record.volumeOfMobileData
                    }

                    var isAlertShow = false

                    records.forEachIndexed { index, record ->
                        if (index + 1 >= records.size) return@forEachIndexed

                        if (record.volumeOfMobileData > records[index + 1].volumeOfMobileData) {
                            isAlertShow = true
                            return@forEachIndexed
                        }
                    }

                    val recordEntity = RecordEntity()
                    recordEntity.year = year
                    recordEntity.quarterRecords = records.toTypedArray()
                    recordEntity.total = String.format("%.2f", total).toDouble()
                    recordEntity.isAlertShow = isAlertShow
                    entities.add(recordEntity)

                    if (year == end) {
                        entities.sortedBy { t -> t.year }
                        it.onNext(entities)
                    }

                }

            })
        }
    }
}