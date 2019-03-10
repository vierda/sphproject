package com.sph.demo.newspaper.common.data.converter

import android.arch.persistence.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.sph.demo.newspaper.common.data.model.Link
import com.sph.demo.newspaper.common.data.model.ResultReport
import com.sph.demo.newspaper.common.util.Util

class LinkConverter {

    internal var gson = Util.initGson()

    @TypeConverter
    fun getLink(data: String): Link {
        val type = object : TypeToken<Link>() {

        }.type
        return gson.fromJson<Link>(data, type)
    }

    @TypeConverter
    fun getStringFromLink(data: Link): String {
        return gson.toJson(data)
    }
}