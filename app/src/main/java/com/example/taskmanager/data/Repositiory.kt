package com.example.taskmanager.data

import kotlinx.coroutines.flow.Flow


class Repository(private val newDao: UserDao, private val taskDao: TaskDao) {
    fun getUser(): Flow<UserEntity?> {
        return newDao.getSingleUser()
    }
        suspend fun addTask(task: TaskEntity) = taskDao.insertTask(task)

        /* suspend fun getUserTasks(userEmail: String) = taskDao.getUserTasks(userEmail)*/
        fun getAllTask(): Flow<List<TaskEntity>> {
            return taskDao.getAllTask()
        }

        suspend fun deletetask(taskId: Int) = taskDao.deleteTask(taskId)


        suspend fun registerUser(user: UserEntity): Boolean {
            return try {
                newDao.registerUser(user)
                true
            } catch (e: Exception) {
                false
            }
        }

        suspend fun login(email: String, password: String): UserEntity? {
            return newDao.loginUser(email, password)
        }


    }
