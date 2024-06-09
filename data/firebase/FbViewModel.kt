package com.example.CourseworkApp.data.firebase

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.Exception
import javax.inject.Inject

//ViewModel for Firebase authentication
@HiltViewModel
class FbViewModel @Inject constructor(
    val auth : FirebaseAuth
) : ViewModel() {

    //State variables for authentication status, progress, and popup notifications
    val signedIn = mutableStateOf(false)
    val inProgress = mutableStateOf(false)
    val popupNotification = mutableStateOf <Event<String>?>(null)

    //Function to handle sign up
    fun onSignup(email: String, pass: String) {
        inProgress.value = true

        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    signedIn.value = true
                    handleException(it.exception, "sign up successful")
                }
                else {
                    handleException(it.exception, "sign up failed")
                }
                inProgress.value = false
            }
    }

    //Function to handle login
    fun login(email: String, pass: String) {
        inProgress.value = true

        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    signedIn.value = true
                    popupNotification.value = Event("login successful")
                }
                else {
                    handleException(it.exception, "login failed")
                }
                inProgress.value = false
            }
    }

    //Function to handle exceptions and display notifications
    fun handleException(exception: Exception? = null, customMessage: String = ""){
        exception?.printStackTrace()
        val errorMsg = exception?.localizedMessage ?: ""
        val message = if (customMessage.isEmpty()) errorMsg else "$customMessage: $errorMsg"
        popupNotification.value = Event(message)

    }


}