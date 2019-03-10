package com.sph.demo.newspaper.common.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.sph.demo.newspaper.common.data.converter.FieldConverter
import com.sph.demo.newspaper.common.data.converter.LinkConverter
import com.sph.demo.newspaper.common.data.converter.RecordConverter

@Entity(tableName = "result_report")
class ResultReport {

    @PrimaryKey
    var resourceId:String = ""

    @TypeConverters(FieldConverter::class)
    lateinit var fields : List<Field>

    @TypeConverters(RecordConverter::class)
    lateinit var records : List<Record>

    @SerializedName("_links")
    @TypeConverters(LinkConverter::class)
    lateinit var links: Link

    var total : Int=0
}