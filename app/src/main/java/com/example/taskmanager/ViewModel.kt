package com.example.taskmanager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.data.Entity
import com.example.taskmanager.data.Repository
import kotlinx.coroutines.launch



class ViewModel(private val repo: Repository=Graph.userRepository, ): ViewModel() {
fun  register(email:String ,password: String,onResult:(Boolean, String)-> Unit){
    viewModelScope.launch {
        val success=repo.registerUser(Entity(email,password))
        if (success){
            onResult(true,"Registered Successful")
        }
        else{
            onResult(false,"Email already exist")
        }
    }
}
    fun login(email: String,password: String,onResult: (Boolean, String) -> Unit){
        viewModelScope.launch {
            val user = repo.login(email, password)
            if (user != null) {
                onResult(true, "Login Successful")
            } else {
                onResult(false, "Invalid Email or Password")
            }

        }
    }
}