package com.sph.demo.newspaper.common.data.converter

import android.arch.persistence.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.sph.demo.newspaper.common.data.model.Field
import com.sph.demo.newspaper.common.util.Util

class FieldConverter {

    internal var gson = Util.initGson()

    @TypeConverter
    fun getListFields(data: String?): List<Field> {
        if (data == null) {
            return emptyList<Field>()
        }

        val listType = object : TypeToken<List<Field>>(){}.type

        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun getStringFromListFields(data: List<Field>): String {
        return gson.toJson(data)
    }
}