package com.roaim.togo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.roaim.togo.data.TogoTypeConverter
import com.roaim.togo.data.model.ToGo

@Database(entities = [ToGo::class], version = 1, exportSchema = false)
@TypeConverters(TogoTypeConverter::class)
abstract class ToGoDB : RoomDatabase() {
    abstract fun toGoDao(): ToGoDao
}