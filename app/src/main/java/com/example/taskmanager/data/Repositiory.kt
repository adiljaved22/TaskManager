package com.example.taskmanager.data

import com.example.taskmanager.User

class Repository(private val newDao: Dao) {
    suspend fun registerUser(user: Entity): Boolean{
        val existing=newDao.emailAlreadyExist(user.email)
        return  if (existing==null){
           newDao.registerUser(user)
           true
       }else
       {
           false
       }
    }
    suspend fun login(email: String,password: String): Entity?{
        return newDao.loginUser(email,password)
    }

}