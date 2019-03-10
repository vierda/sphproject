package com.sph.demo.newspaper

import android.app.Application
import android.arch.persistence.room.Room
import com.sph.demo.newspaper.common.data.database.AppDatabase
import com.sph.demo.newspaper.common.network.WebApiService

class NewspaperApplication : Application() {

    companion object {

        lateinit var _instance: NewspaperApplication

        fun getInstance(): NewspaperApplication {
            return _instance
        }
    }

    private val DATABASE_NAME = "NewspaperDb"
    private var database: AppDatabase? = null
    private var webservice : WebApiService? = null


    override fun onCreate() {
        super.onCreate()
        _instance = this

        database = Room.databaseBuilder(applicationContext, AppDatabase::class.java, DATABASE_NAME)
                .build()

        webservice = WebApiService.create()
    }

    fun getDatabase(): AppDatabase? {
        return database
    }

    fun getWebService(): WebApiService?{
        return webservice
    }

}