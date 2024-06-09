package com.example.CourseworkApp.data.session

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface SessionDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertSession(session: Session)

    @Delete
    fun deleteSession(session: Session)

    @Update
    fun updateSession(session: Session)

    @Query("SELECT * FROM Sessions WHERE sessionId = :sessionId")
    fun getSessionById(sessionId: Int): LiveData<Session>

    @Query("SELECT * FROM Sessions WHERE userId = :userId")
    fun getSessionsByUserId(userId: Int): LiveData<List<Session>>

    @Query("SELECT * FROM Sessions")
    fun getAllSessions(): LiveData<List<Session>>

    @Query("SELECT MAX(sessionId) FROM sessions")
    suspend fun getLastInsertedSessionId(): Int?

}