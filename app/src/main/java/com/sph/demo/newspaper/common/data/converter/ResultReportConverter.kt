package com.sph.demo.newspaper.common.data.converter

import android.arch.persistence.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.sph.demo.newspaper.common.data.model.ResultReport
import com.sph.demo.newspaper.common.util.Util

class ResultReportConverter {

    internal var gson = Util.initGson()

    @TypeConverter
    fun getResultReport(data: String): ResultReport {
        val type = object : TypeToken<ResultReport>() {

        }.type
        return gson.fromJson<ResultReport>(data, type)
    }

    @TypeConverter
    fun getStringFromResultReport(data: ResultReport): String {
        return gson.toJson(data)
    }
}