package com.example.taskmanager.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface  Dao{
    @Insert(onConflict = OnConflictStrategy.ABORT)
    abstract suspend fun registerUser(user: Entity)
    @Query("SELECT * FROM users WHERE email=:email AND password=:password LIMIT 1")
    suspend fun loginUser(email: String,password: String): Entity?
    @Query("SELECT * FROM users WHERE email=:email LIMIT 1")
    suspend fun emailAlreadyExist(email: String):Entity?
}