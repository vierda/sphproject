package com.sph.demo.newspaper.common.data.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import com.sph.demo.newspaper.common.data.model.ResultReport

@Dao
interface ResultReportDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(resultReport: ResultReport)
}