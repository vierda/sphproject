package com.sph.demo.newspaper.main.data.entity

import com.sph.demo.newspaper.common.data.model.Record

class RecordEntity {

    var year : Int = 0
    var isAlertShow : Boolean = false
    lateinit var quarterRecords : Array<Record>
    var total : Double =0.0
}
