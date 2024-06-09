package com.example.CourseworkApp.data.kick

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

//This is my Kick Repository : the only method I needed was insertKick therefore I did not create any other methods
class KickRepository (private val kickDao: KickDao){
    //Here I have fetched the LiveData of all kicks in the database using the DAO's getAllKicks method
    val allKicks: LiveData<List<Kick>> = kickDao.getAllKicks()

    //This function accepts a kick object as input and inserts the given kick using the DAO's insertKick method
    suspend fun insertKick (kick: Kick){
        withContext(Dispatchers.IO) {
            kickDao.insertKick(kick)
        }
    }

}