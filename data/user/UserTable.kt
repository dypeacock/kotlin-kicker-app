package com.example.CourseworkApp.data.user

import androidx.room.Entity
import androidx.room.PrimaryKey

// This is my User Table
@Entity(tableName = "Users")
data class User(
    @PrimaryKey(autoGenerate = true) val userId: Int = 0,
    val email: String,
    val password: String
)