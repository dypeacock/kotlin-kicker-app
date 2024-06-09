package com.example.CourseworkApp.data.firebase

//Event class for handling events with content
open class Event<out T>(private val content : T) {

    var hasBeenHandled = false
        private set

    //Retrieve content if event hasn't been handled, mark it as handled otherwise
    fun getContentOrNull() : T? {
        return if (hasBeenHandled) {
            null
        }
        else {
            hasBeenHandled = true
            content
        }
    }
}