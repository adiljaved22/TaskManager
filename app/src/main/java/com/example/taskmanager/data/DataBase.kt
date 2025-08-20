package com.example.taskmanager.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Entity::class],
    version = 1,
    exportSchema = false
)
abstract class DataBase() : RoomDatabase() {
    abstract fun dao(): Dao
}
