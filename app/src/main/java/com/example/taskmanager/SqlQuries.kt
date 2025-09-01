/*
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun registerUser(user: UserEntity)

    @Query("SELECT * FROM users LIMIT 1")
    fun getSingleUser(): Flow<UserEntity?>


    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    suspend fun loginUser(email: String, password: String): UserEntity?

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun emailAlreadyExist(email: String): UserEntity?
}

@Dao
interface TaskDao {

    @Insert
    suspend fun insertTask(task: TaskEntity)

    @Query("SELECT * FROM tasks")
    fun getAllTask(): Flow<List<TaskEntity>>

    *//*    @Query("SELECT * FROM tasks WHERE userEmail = :email")
        suspend fun getUserTasks(email: String): List<TaskEntity>*//*


    @Query("Update tasks SET title= :newTitle, description=:newDescription WHERE id =:taskId")
    suspend fun update(taskId: Int, newTitle : String, newDescription: String)



    @Query("DELETE FROM tasks WHERE id = :taskId")
    suspend fun deleteTask(taskId: Int)
*/
package com.example.taskmanager.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqlQuries(context: Context) :
    SQLiteOpenHelper(context, DataBase_Name, null, DataBase_Version) {

    companion object {
        const val DataBase_Name = "TaskManager.db"
        const val DataBase_Version = 1

        /* Task Table */
        const val TaskTable = "TaskEntity"
        const val ColumnId = "id"
        const val ColumnTitle = "title"
        const val ColumnDescription = "description"

        /* User Table */
        const val UserTable = "UserEntity"
        const val ColumnEmail = "email"
        const val ColumnPassword = "password"
        const val ColumnUsername = "username"
        const val ColumnImageUri = "imageUri"
        const val ColumnDateOfBirth = "dateOfBirth"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val taskTable = """
            CREATE TABLE $TaskTable(
                $ColumnId INTEGER PRIMARY KEY AUTOINCREMENT,
                $ColumnTitle TEXT,
                $ColumnDescription TEXT
            )
        """.trimIndent()

        val userTable = """
            CREATE TABLE $UserTable(
                $ColumnEmail TEXT PRIMARY KEY,
                $ColumnPassword TEXT,
                $ColumnUsername TEXT,
                $ColumnImageUri TEXT,
                $ColumnDateOfBirth TEXT
            )
        """.trimIndent()

        db?.execSQL(taskTable)
        db?.execSQL(userTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $UserTable")
        db?.execSQL("DROP TABLE IF EXISTS $TaskTable")
        onCreate(db)
    }


    fun insertTask(task: TaskEntity): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(ColumnTitle, task.title)
            put(ColumnDescription, task.description)
        }
        val result = db.insert(TaskTable, null, values)
        db.close()
        return result
    }

    fun getAllTask(): MutableList<TaskEntity> {
        val taskList = mutableListOf<TaskEntity>()
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TaskTable", null)
        if (cursor.moveToFirst()) {
            do {
                val task = TaskEntity(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(ColumnId)),
                    title = cursor.getString(cursor.getColumnIndexOrThrow(ColumnTitle)),
                    description = cursor.getString(cursor.getColumnIndexOrThrow(ColumnDescription))
                )
                taskList.add(task)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return taskList
    }

    fun updateTask(taskId: Int, newTitle: String, newDescription: String): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(ColumnTitle, newTitle)
            put(ColumnDescription, newDescription)
        }
        val result = db.update(TaskTable, values, "$ColumnId=?", arrayOf(taskId.toString()))
        db.close()
        return result
    }

    fun deleteTask(taskId: Int): Int {
        val db = writableDatabase
        val result = db.delete(TaskTable, "$ColumnId=?", arrayOf(taskId.toString()))
        db.close()
        return result
    }


    fun registerUser(
        name: String, email: String, password: String, imageUri: String?, dateOfBirth: String?
    ): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(ColumnUsername, name)
            put(ColumnEmail, email)
            put(ColumnPassword, password)
            put(ColumnImageUri, imageUri)
            put(ColumnDateOfBirth, dateOfBirth)
        }
        val result = db.insert(UserTable, null, values)
        db.close()
        return result > 0
    }

    fun getSingleUser(): UserEntity? {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $UserTable LIMIT 1", null)
        var user: UserEntity? = null
        if (cursor.moveToFirst()) {
            user = UserEntity(
                email = cursor.getString(cursor.getColumnIndexOrThrow(ColumnEmail)),
                password = cursor.getString(cursor.getColumnIndexOrThrow(ColumnPassword)),
                username = cursor.getString(cursor.getColumnIndexOrThrow(ColumnUsername)),
                imageUri = cursor.getString(cursor.getColumnIndexOrThrow(ColumnImageUri)),
                dateOfBirth = cursor.getString(cursor.getColumnIndexOrThrow(ColumnDateOfBirth))
            )
        }
        cursor.close()
        db.close()
        return user
    }

    fun loginUser(email: String, password: String): Boolean {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $UserTable WHERE $ColumnEmail=? AND $ColumnPassword=?",
            arrayOf(email, password)
        )
//        var user: UserEntity? = null
//        if (cursor.moveToFirst()) {
//            user = UserEntity(
//                email = cursor.getString(cursor.getColumnIndexOrThrow(ColumnEmail)),
//                password = cursor.getString(cursor.getColumnIndexOrThrow(ColumnPassword)),
//                username = cursor.getString(cursor.getColumnIndexOrThrow(ColumnUsername)),
//                imageUri = cursor.getString(cursor.getColumnIndexOrThrow(ColumnImageUri)),
//                dateOfBirth = cursor.getString(cursor.getColumnIndexOrThrow(ColumnDateOfBirth))
//            )
//        }
        val cursorCount: Int = cursor.count
        cursor.close()
        db.close()
        return cursorCount > 0
    }
}

