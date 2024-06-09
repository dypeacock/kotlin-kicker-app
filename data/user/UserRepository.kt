package com.example.CourseworkApp.data.user

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

//This is my User Repository :
class UserRepository(private val userDao: UserDao) {
    //Here I have fetched the LiveData of all Users in the database using the DAO's getAllUsers method
    val allUsers: LiveData<List<User>> = userDao.getAllUsers()

    //This function accepts a user object as input and inserts the given user using the DAO's insert method
    suspend fun insert(user: User) {
        withContext(Dispatchers.IO) {
            userDao.insert(user)
        }

    }

    //This function does not accept any input and returns an integer equal to the last inserted user's userId using the DAO's getLastInsertedUserId method
    suspend fun getLastInsertedUserId(): Int? {
        return userDao.getLastInsertedUserId()
    }

    //This function takes a user's userId as input and returns the LiveData of all users with that userId using the DAO's getUserById method
    fun getUserByID(id: Int): LiveData<User> {
        return userDao.getUserById(id)
    }

}