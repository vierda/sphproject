package com.sph.demo.newspaper.common.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "link")
class Link {

    @PrimaryKey(autoGenerate = true)
    var id : Int = 0

    var start:String=""
    var next:String=""
}