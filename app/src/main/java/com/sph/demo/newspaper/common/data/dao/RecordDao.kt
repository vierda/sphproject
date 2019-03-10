package com.sph.demo.newspaper.common.data.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.sph.demo.newspaper.common.data.model.Record

@Dao
interface RecordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(record: Record)

    @Query("SELECT * FROM record WHERE quarter LIKE :year")
    abstract fun selectRecordsByYear(year: String): List<Record>

    @Insert
    abstract fun insertAll(records: List<Record>)
}