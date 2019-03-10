package com.sph.demo.newspaper.common.data.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import com.sph.demo.newspaper.common.data.model.Field

@Dao
interface FieldDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(field: Field)
}