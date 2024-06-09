package com.example.CourseworkApp.data.firebase

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

//Hilt module providing FirebaseAuth instance
@Module
@InstallIn(ViewModelComponent::class)
class HiltModule {

    @Provides
    fun provideAuthentication(): FirebaseAuth = Firebase.auth

}