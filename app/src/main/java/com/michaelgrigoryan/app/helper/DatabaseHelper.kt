package com.michaelgrigoryan.app.helper

import android.content.Context
import androidx.room.Room
import com.michaelgrigoryan.app.db.Database
import com.michaelgrigoryan.app.db.User

class DatabaseHelper(context: Context) {
    private val db = Room
        .databaseBuilder(context, Database::class.java, "app-db")
        .fallbackToDestructiveMigration()
        .build()

    fun getAll(): List<User> = db.userDao().getAll()
    fun insertAll(users: User) = db.userDao().insertAll(users)
    fun delete(user: User) = db.userDao().delete(user)
    suspend fun update(user: User) = db.userDao().updateUser(user)
}