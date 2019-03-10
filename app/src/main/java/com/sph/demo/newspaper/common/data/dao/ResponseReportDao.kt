package com.sph.demo.newspaper.common.data.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.sph.demo.newspaper.common.data.model.ResponseReport

@Dao
interface ResponseReportDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(responseReport: ResponseReport)

    @Query("SELECT count(*) FROM response_report")
    abstract fun getResponseCount():Int
}