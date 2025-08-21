package com.example.taskmanager.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
@Entity(
    tableName = "tasks",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["email"],
            childColumns = ["userEmail"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val userEmail: String
)
@Entity("users")
data class UserEntity(
    @PrimaryKey
    val email: String,
    val password : String,
    val username: String,
    val imageuri: String,
    val dateofbirth:String?=null
)