package com.sph.demo.newspaper.common.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.sph.demo.newspaper.common.data.dao.*
import com.sph.demo.newspaper.common.data.model.*

@Database(entities = [Field::class, Link::class,
    Record::class, ResponseReport::class, ResultReport::class], version = 1)
abstract class AppDatabase : RoomDatabase(){

    abstract fun FieldDao(): FieldDao
    abstract fun LinkDao(): LinkDao
    abstract fun RecordDao(): RecordDao
    abstract fun ResponseReportDao(): ResponseReportDao
    abstract fun ResultReportDao(): ResultReportDao

}