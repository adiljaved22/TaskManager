package com.example.taskmanager

import android.content.Context
import androidx.room.Room
import com.example.taskmanager.data.userDataBase
import com.example.taskmanager.data.Repository

object Graph {
    lateinit var database: userDataBase
    val userRepository by lazy {
        Repository(newDao = database.dao(), taskDao = database.taskDao())
    }
    fun provide(context: Context){
        database= Room.databaseBuilder(
            context,
            userDataBase::class.java,
            name = "Registration.db"
        ).fallbackToDestructiveMigration()
            .build()

    }
}