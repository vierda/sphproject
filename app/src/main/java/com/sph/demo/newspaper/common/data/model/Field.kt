package com.sph.demo.newspaper.common.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "field")
class Field {

    @PrimaryKey(autoGenerate = true)
    var fieldId : Int = 0

    var type:String=""
    var id:String=""

}