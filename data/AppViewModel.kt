package com.example.CourseworkApp.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.CourseworkApp.data.kick.Kick
import com.example.CourseworkApp.data.kick.KickRepository
import com.example.CourseworkApp.data.session.Session
import com.example.CourseworkApp.data.session.SessionRepository
import com.example.CourseworkApp.data.user.User
import com.example.CourseworkApp.data.user.UserRepository
import kotlinx.coroutines.launch

//ViewModel for the application
class AppViewModel (application: Application) : AndroidViewModel(application) {
    //User repository and LiveData
    private val userRepository: UserRepository
    val allUsers: LiveData<List<User>>

    //Session repository and LiveData
    private val sessionRepository : SessionRepository
    val allSessions : LiveData<List<Session>>

    //Kick repository and LiveData
    private val kickRepository: KickRepository
    val allKicks: LiveData<List<Kick>>

    //Initialise repositories and LiveData objects
    init {
        val userDao = AppDatabase.getDatabase(application).userDao()
        userRepository = UserRepository(userDao)
        allUsers = userRepository.allUsers

        val sessionDao = AppDatabase.getDatabase(application).sessionDao()
        sessionRepository = SessionRepository(sessionDao)
        allSessions = sessionRepository.allSessions

        val kickDao = AppDatabase.getDatabase(application).kickDao()
        kickRepository = KickRepository(kickDao)
        allKicks = kickRepository.allKicks
    }

    // ------------------------------------------------------- //

    //User methods

    //Variable to hold the last inserted user ID
    private var lastInsertedUserID: Int? = null

    //Insert user asynchronously and provide callback with last inserted ID
    fun insert(user: User, callback: (Int?) -> Unit) = viewModelScope.launch {
        userRepository.insert(user)
        lastInsertedUserID = userRepository.getLastInsertedUserId()
        callback(lastInsertedUserID)
    }

    //Get user by ID
    fun getUserByID(id: Int) : LiveData<User> {
        return userRepository.getUserByID(id)
    }

    //Session methods

    //Insert session asynchronously
    fun insert(session: Session) = viewModelScope.launch {
        sessionRepository.insertSession(session)
    }

    //Get sessions by user ID
    suspend fun getSessionsByUserID(userID: Int): LiveData<List<Session>> {
        return sessionRepository.getSessionsByUserID(userID)
    }

    // Kick methods

    // Insert kick asynchronously
    fun insert(kick: Kick) = viewModelScope.launch {
        kickRepository.insertKick(kick)
    }

    // ------------------------------------------------------- //

    //Variables to manage pending kicks and current session
    private val pendingKicks = mutableListOf<Kick>()
    private var currentSession: Session? = null

    //Start a new session : creates a Session object and assigns it to the currentSession variable
    fun startSession(date: String, type: String, userId: Int) {
        currentSession = Session(date = date, sessionType = type, userId = userId)
    }

    //Record a kick during the session : creates a Kick object and adds it to the pendingKicks list
    fun recordKick(distance: Int, isSuccess: Boolean, area: String) {
        currentSession?.sessionId?.let { sessionId ->
            val kick = Kick(sessionId = sessionId, success = isSuccess, area = area, distance = distance)
            pendingKicks.add(kick)
        }
    }

    //End the current session : wait for the session to be inserted before retrieving its id to insert the associated kicks
    fun endSession() {
        currentSession?.let { session ->
            viewModelScope.launch {
                //Insert the session
                sessionRepository.insertSession(session)
                //Now that the session is inserted, we can retrieve its ID and update pendingKicks
                val sessionId = sessionRepository.getLastInsertedSessionId()
                pendingKicks.forEach { kick ->
                    //Default to 0 if session ID is null
                    kick.sessionId = sessionId ?: 0
                    //Insert the kick into the database
                    insert(kick)
                }
                //Clear pending kicks
                pendingKicks.clear()
            }
        }
    }

    //Abort the current session : resets the current session and pending kicks
    fun abortSession(){
        currentSession = null
        pendingKicks.clear()

    }
}