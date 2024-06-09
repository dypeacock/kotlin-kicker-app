package com.example.CourseworkApp.data.kick

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface KickDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertKick(kick: Kick)

    @Update
    fun updateKick(kick: Kick)

    @Delete
    fun deleteKick(kicks: Kick)

    @Query("SELECT * FROM Kicks WHERE sessionId = :sessionId")
    fun getKicksBySessionId(sessionId: Int): LiveData<List<Kick>>

    @Query("SELECT * FROM Kicks")
    fun getAllKicks(): LiveData<List<Kick>>
}