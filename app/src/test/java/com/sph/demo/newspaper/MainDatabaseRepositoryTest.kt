package com.sph.demo.newspaper

import com.sph.demo.newspaper.common.data.model.Record
import com.sph.demo.newspaper.common.data.model.ResponseReport
import com.sph.demo.newspaper.main.data.MainDatabaseRepository
import com.sph.demo.newspaper.main.data.entity.RecordEntity
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class MainDatabaseRepositoryTest {

    lateinit var databaseRepository: MainDatabaseRepository

    @Before
    fun setup() {

        databaseRepository = mock(MainDatabaseRepository::class.java)
    }

    @Test
    fun saveReportTest() {

        val responseReport = ResponseReport()

        `when`(databaseRepository.saveReport(responseReport)).thenReturn(Observable.just(true))

        databaseRepository.saveReport(responseReport)
                .test()
                .assertValue { it.equals(true) }
                .assertComplete()
    }

    @Test
    fun getResourceCountTest() {

        `when`(databaseRepository.getResourceCount()).thenReturn(Observable.just(100))
        databaseRepository.getResourceCount()
                .test()
                .assertValue { it.equals(100) }
                .assertValue { it != 1 }
                .assertComplete()
    }

    @Test
    fun selectAllRecordsTest() {

        val recordEntity = RecordEntity()
        recordEntity.year = 2008
        recordEntity.quarterRecords = listOf<Record>(createRecord(1, 5.0, "2008-Q1")).toTypedArray()
        recordEntity.total = 10.0
        recordEntity.isAlertShow = true

        val entities = listOf<RecordEntity>(recordEntity)

        `when`(databaseRepository.selectAllRecords(2008, 2010)).thenReturn(Observable.just(entities))

        databaseRepository.selectAllRecords(2008, 2010)
                .test()
                .assertValue { it.isNotEmpty() }
                .assertValue { it.size == 1 }
                .assertValue { it[0].isAlertShow == true }
                .assertValue { it[0].total==10.0 }
                .assertValue { it[0].quarterRecords.isNotEmpty() }
                .assertValue { it[0].quarterRecords.size==1 }
                .assertValue { it[0].quarterRecords[0].quarter.equals("2008-Q1") }
                .assertValue { it[0].quarterRecords[0].id ==1 }
                .assertValue { it[0].quarterRecords[0].volumeOfMobileData==5.0 }
                .assertComplete()

    }

    fun createRecord(id: Int, volume: Double, quarter: String): Record {
        val record = Record()

        record.id = id
        record.volumeOfMobileData = volume
        record.quarter = quarter

        return record
    }
}