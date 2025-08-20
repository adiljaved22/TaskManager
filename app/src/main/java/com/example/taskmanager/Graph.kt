package com.example.taskmanager

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import com.example.taskmanager.data.DataBase
import com.example.taskmanager.data.Repository

object Graph {
    lateinit var database: DataBase
    val userRepository by lazy {
        Repository(newDao = database.dao())
    }
    fun provide(context: Context){
        database= Room.databaseBuilder(
            context,
            DataBase::class.java,
            name = "Registration.db"
        ).build()

    }
}