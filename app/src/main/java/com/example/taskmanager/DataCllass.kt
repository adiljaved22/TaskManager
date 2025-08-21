package com.example.taskmanager

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import com.example.taskmanager.data.TaskEntity
import com.example.taskmanager.data.UserEntity


data class User(
    val username: String,
    val profileImage: Uri?
)

object UserSession {
    var currentUser: UserEntity? = null
}

object Responsible {
    val savedlist = mutableStateListOf<TaskEntity>()
}