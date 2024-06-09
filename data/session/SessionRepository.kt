package com.example.CourseworkApp.data.session

import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.LiveData
import kotlinx.coroutines.withContext

//This is my Session Repository :
class SessionRepository (private val sessionDao: SessionDao) {
    //Here I have fetched the LiveData of all sessions in the database using the DAO's getAllSessions method
    val allSessions: LiveData<List<Session>> = sessionDao.getAllSessions()

    //This function accepts a session object as input and inserts the given session using the DAO's insertSession method
    suspend fun insertSession (session: Session){
        withContext(Dispatchers.IO) {
            sessionDao.insertSession(session)
        }
    }

    //This function does not accept any input and returns an integer equal to the last inserted session's sessionId using the DAO's getLastInsertedSessionId method
    suspend fun getLastInsertedSessionId() : Int?{
        return sessionDao.getLastInsertedSessionId()
    }

    //This function takes a user's userId as input and returns the LiveData of all sessions with that user's userId using the DAO's getSessionsByUserId method
    suspend fun getSessionsByUserID (userId: Int) : LiveData<List<Session>> {
        return sessionDao.getSessionsByUserId(userId)
    }


}