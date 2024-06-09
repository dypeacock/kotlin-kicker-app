package com.example.CourseworkApp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.CourseworkApp.data.kick.Kick
import com.example.CourseworkApp.data.kick.KickDao
import com.example.CourseworkApp.data.session.Session
import com.example.CourseworkApp.data.session.SessionDao
import com.example.CourseworkApp.data.user.User
import com.example.CourseworkApp.data.user.UserDao

//This is the app's room database : declares Kick, Session, and User as entities
@Database(entities = [Kick::class, Session::class, User::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    //Abstract methods for accessing DAOs for Kick, Session, and User.
    abstract fun kickDao(): KickDao
    abstract fun sessionDao(): SessionDao
    abstract fun userDao(): UserDao

    //Companion object gets an instance of the AppDatabase
    companion object {
        //Checks if INSTANCE is null and if so, it creates a new instance
        @Volatile
        private var INSTANCE : AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "AppDatabase"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
