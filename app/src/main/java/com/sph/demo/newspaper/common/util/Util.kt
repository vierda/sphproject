package com.sph.demo.newspaper.common.util

import com.google.gson.Gson
import com.google.gson.GsonBuilder

class Util {

    companion object {
        var gson = initGson()

        fun initGson(): Gson {
            val builder = GsonBuilder()
            if (gson == null)
                gson = builder.create()
            return gson
        }
    }


}