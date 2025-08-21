package com.example.taskmanager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.taskmanager.data.Repository
import com.example.taskmanager.data.TaskEntity
import com.example.taskmanager.data.UserEntity
import kotlinx.coroutines.launch



class userViewModel(private val repo: Repository=Graph.userRepository, ): ViewModel() {
    fun  register(user: UserEntity, onResult:(Boolean, String)-> Unit){
        viewModelScope.launch {
            val success=repo.registerUser(user)
            if (success){
                onResult(true,"Registered Successful")
            }
            else{
                onResult(false,"Email already exist")
            }
        }
    }
    fun login(email: String,password: String,onResult: (Boolean, String, UserEntity?) -> Unit) {
        viewModelScope.launch {
            val user = repo.login(email, password)
            if (user != null) {
                onResult(true, "Login Successful", user)
            } else {
                onResult(false, "Invalid Email or Password", null)
            }

        }
    }


    fun loadTasks(userEmail: String) {
        viewModelScope.launch {
            val list = repo.getUserTasks(userEmail)
            Responsible.savedlist.clear()
            Responsible.savedlist.addAll(list)
        }
    }

    // 🔹 Add Task
    fun addTask(title: String, description: String, userEmail: String) {
        viewModelScope.launch {
            val task = TaskEntity(
                title = title,
                description = description,
                userEmail = userEmail
            )
            repo.addTask(task)
            Responsible.savedlist.add(task) // UI update
        }
    }


    fun deletetask(taskId: Int) {
        viewModelScope.launch {
            repo.deletetask(taskId)
            Responsible.savedlist.removeAll { it.id == taskId }
        }
    }
}