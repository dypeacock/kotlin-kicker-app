package com.example.CourseworkApp.data.firebase

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

//Function to display notification messages as Toast
@Composable
fun NotificationMessage(vm: FbViewModel) {
    val notifState = vm.popupNotification.value
    val notifMessage = notifState?.getContentOrNull()
    if (notifMessage != null) {
        Toast.makeText(LocalContext.current, notifMessage, Toast.LENGTH_SHORT).show()
    }
}
