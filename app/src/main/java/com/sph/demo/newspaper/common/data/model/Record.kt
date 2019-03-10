package com.sph.demo.newspaper.common.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "record")
class Record {

    @PrimaryKey(autoGenerate = true)
    var recordId:Int=0

    @SerializedName("_id")
    var id:Int=0

    @SerializedName("volume_of_mobile_data")
    var volumeOfMobileData: Double=0.0
    var quarter:String =""

}