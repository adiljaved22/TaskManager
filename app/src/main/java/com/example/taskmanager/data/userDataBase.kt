package com.example.taskmanager.data

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [UserEntity::class, TaskEntity::class],
    version = 3,
    exportSchema = false
)
abstract class userDataBase : RoomDatabase() {
    abstract fun dao(): UserDao
    abstract fun taskDao(): TaskDao
}