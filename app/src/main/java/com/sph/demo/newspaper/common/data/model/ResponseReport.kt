package com.sph.demo.newspaper.common.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.sph.demo.newspaper.common.data.converter.ResultReportConverter

@Entity(tableName = "response_report")
class ResponseReport {

    @PrimaryKey(autoGenerate = true)
    var id : Int = 0

    var help : String = ""
    var success : Boolean = false


    @TypeConverters(ResultReportConverter::class)
    lateinit var  result : ResultReport
}