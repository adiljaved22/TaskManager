package com.example.taskmanager.data


class Repository(private val newDao: UserDao, private val taskDao: TaskDao) {

    suspend fun addTask(task: TaskEntity) = taskDao.insertTask(task)
    suspend fun getUserTasks(userEmail: String) = taskDao.getUserTasks(userEmail)
    suspend fun deletetask(taskId: Int)=taskDao.deleteTask(taskId)


    suspend fun registerUser(user: UserEntity): Boolean{
        val existing=newDao.emailAlreadyExist(user.email)
        return  if (existing==null){
            newDao.registerUser(user)
            true
        }else
        {
            false
        }
    }
    suspend fun login(email: String,password: String): UserEntity?{
        return newDao.loginUser(email,password)
    }



}