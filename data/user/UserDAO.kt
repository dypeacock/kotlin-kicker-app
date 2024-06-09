package com.example.CourseworkApp.data.user

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("SELECT * FROM Users WHERE email = :email")
    fun getUserByEmail(email: String): LiveData<User>

    @Query("SELECT * FROM Users WHERE userId = :id")
    fun getUserById(id: Int): LiveData<User>

    @Query("SELECT * FROM Users")
    fun getAllUsers(): LiveData<List<User>>

    @Query("SELECT MAX(userId) FROM Users")
    suspend fun getLastInsertedUserId(): Int?
}