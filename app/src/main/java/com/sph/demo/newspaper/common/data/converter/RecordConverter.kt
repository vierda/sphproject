package com.sph.demo.newspaper.common.data.converter

import android.arch.persistence.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.sph.demo.newspaper.common.data.model.Record
import com.sph.demo.newspaper.common.util.Util

class RecordConverter {

    internal var gson = Util.initGson()

    @TypeConverter
    fun getListRecords(data: String?): List<Record> {
        if (data == null) {
            return emptyList()
        }

        val listType = object : TypeToken<List<Record>>(){}.type

        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun getStringFromListRecords(data: List<Record>): String {
        return gson.toJson(data)
    }

}