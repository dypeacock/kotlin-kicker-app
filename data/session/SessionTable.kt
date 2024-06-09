package com.example.CourseworkApp.data.session

import androidx.room.Entity
import androidx.room.PrimaryKey

//This is my Session Table : userId references the userId column of the User table although it is not explicitly defined
@Entity(tableName = "Sessions")
data class Session(
    @PrimaryKey(autoGenerate = true) var sessionId: Int = 0,
    val userId: Int,
    val date: String,
    val sessionType: String
)