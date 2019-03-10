package com.sph.demo.newspaper.main.presentation.view

import com.sph.demo.newspaper.common.data.model.Record

interface MainDataView {

    fun showRecordChart(year:String?,quarterRecords :Array<Record>?)
    fun hideProgressLoading()
}