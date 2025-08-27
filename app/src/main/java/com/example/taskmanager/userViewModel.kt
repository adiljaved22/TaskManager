package com.example.taskmanager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.taskmanager.data.Repository
import com.example.taskmanager.data.TaskEntity
import com.example.taskmanager.data.UserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.internal.concurrent.Task


/*class userViewModel(
    private val repo: Repository = Graph.userRepository
) : ViewModel() {
    val getall: Flow<List<TaskEntity>> = repo.getAllTask()


*//*    fun getUser(): Flow<UserEntity?> = repo.getUser()*//*


    fun register(user: UserEntity, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            val success = repo.registerUser(user)
            if (success) {
                onResult(true, "Registered Successful")
            } else {
                onResult(false, "Email already exist")
            }
        }
    }


    fun login(email: String, password: String, onResult: (Boolean, String, UserEntity?) -> Unit) {
        viewModelScope.launch {
            val user = repo.login(email, password)
            if (user != null) {
                onResult(true, "Login Successful", user)
            } else {
                onResult(false, "Invalid Email or Password", null)
            }
        }
    }


    fun addTask(task: TaskEntity) {
        viewModelScope.launch {
            repo.addTask(task)
        }
    }


    fun deletetask(taskId: Int) {
        viewModelScope.launch {
            repo.deletetask(taskId)
        }
    }
}*/


class userViewModel(private val repo: Repository=Graph.userRepository, ): ViewModel() {
    private val _tasks = MutableStateFlow<List<TaskEntity>>(emptyList())
    val get: MutableStateFlow<List<TaskEntity>> = _tasks
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

    lateinit var getall:Flow<List<TaskEntity>>
    init {
        getall = repo.getAllTask()
    }
   fun getUser(): Flow<UserEntity?> {
        return repo.getUser()
    }
  /*  fun loadTasks(userEmail: String) {
        viewModelScope.launch {
            val list = repo.getUserTasks(userEmail)
            Responsible.savedlist.clear()
            Responsible.savedlist.addAll(list)
        }
    }*/


    fun addTask(task: TaskEntity) {
        viewModelScope.launch {
            repo.addTask(task)
        }
    }



    fun deletetask(taskId: Int) {
        viewModelScope.launch {
            repo.deletetask(taskId)
            Responsible.savedlist.removeAll { it.id == taskId }
        }
    }
}
