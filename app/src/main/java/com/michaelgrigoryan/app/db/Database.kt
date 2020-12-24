package com.michaelgrigoryan.app.db

import androidx.room.RoomDatabase
import androidx.room.Database

@Database(entities = [User::class], version = 4)
abstract class Database: RoomDatabase() {
    abstract fun userDao(): UserDao
}