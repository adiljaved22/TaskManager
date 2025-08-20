package com.example.taskmanager.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("users")
data class Entity(
    @PrimaryKey
    val email: String,
    val password : String
)