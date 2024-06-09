package com.example.CourseworkApp.data.kick

import androidx.room.Entity
import androidx.room.PrimaryKey

//This is my Kick Table : sessionId references the sessionId column of the Session table although it is not explicitly defined
@Entity(tableName = "Kicks")
data class Kick(
    @PrimaryKey(autoGenerate = true) val kickId: Int = 0,
    var sessionId: Int,
    val success: Boolean,
    val area: String,
    val distance: Int
)
