package com.example.taskmanager

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf

data class User(
    val username: String,
    val profileImage: Uri?
)

object UserSession {
    var currentUser: User? = null
}

data class TaskItem
    (
    val title: String,
    val description: String
)
object Responsible{
    val formlist = mutableStateListOf<TaskItem>()
    val savedlist=mutableStateListOf<TaskItem>()
}