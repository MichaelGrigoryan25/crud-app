package com.michaelgrigoryan.app.db
import androidx.room.*

@Dao
interface UserDao {
    // Create a contact
    @Insert
    fun insertAll(vararg users: User)

    // Read all contacts
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    // Update a contact
    @Update
    suspend fun updateUser(user: User)

    // Delete a contact
    @Delete
    fun delete(user: User)
}