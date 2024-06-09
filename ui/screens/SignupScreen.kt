package com.example.CourseworkApp.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.CourseworkApp.DestinationScreen
import com.example.CourseworkApp.data.firebase.FbViewModel
import com.example.CourseworkApp.R
import com.example.CourseworkApp.data.AppViewModel
import com.example.CourseworkApp.data.user.User


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(navController: NavController, vm: FbViewModel, appViewModel: AppViewModel) {
    //Observes the list of all users from the AppViewModel
    val users by appViewModel.allUsers.observeAsState(initial = emptyList())

    //Variables to store email, password, confirmation password, and their error states
    val empty by rememberSaveable { mutableStateOf("")}
    var email by rememberSaveable { mutableStateOf("")}
    var password by rememberSaveable { mutableStateOf("")}
    var cpassword by rememberSaveable { mutableStateOf("")}
    var passwordVisibility by rememberSaveable { mutableStateOf(false)}
    var cpasswordVisibility by rememberSaveable { mutableStateOf(false)}
    var errorE by rememberSaveable { mutableStateOf(false)}
    var errorP by rememberSaveable { mutableStateOf(false)}
    var errorCP by rememberSaveable { mutableStateOf(false)}
    var errorC by rememberSaveable { mutableStateOf(false)}
    var plength by rememberSaveable { mutableStateOf(false)}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFE6E6E6))
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        //If a network operation is in progress, show a progress indicator
        if (vm.inProgress.value) {
            CircularProgressIndicator()
        }
    }

    //Column for the signup form
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp)
            .verticalScroll(
                rememberScrollState()
            )
    ) {

        //Box to display user icon
        Box(
            modifier = Modifier
                .background(color = Color.White, shape = CircleShape)
                .size(width = 200.dp, height = 200.dp),
            contentAlignment = Alignment.TopCenter

        ){
            Icon(
                painter = painterResource(id = R.drawable.baseline_person_24),
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier
                    .size(250.dp)
                    .clip(CircleShape)
            )
        }

        //Sign Up text
        Text(
            text = "Sign Up",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp,
        )

        Spacer(modifier = Modifier.height(50.dp))

        //If there is an email related error, display error message
        if (errorE) {
            Text(
                text = "Enter email",
                color = Color.Red,
                modifier = Modifier.padding(end = 100.dp)
            )
        }

        //TextField for email input
        TextField(
            value = email,
            onValueChange = {
                email = it
            },
            label = {
                Text(text = "Email")
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_person_24),
                    contentDescription = null
                )
            },
            trailingIcon = {
                if (email.isNotEmpty()){
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_close_24),
                        contentDescription = null,
                        Modifier.clickable { email = empty }
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            singleLine = true,
            textStyle = TextStyle(
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            ),
            shape = RoundedCornerShape(50.dp),
            modifier = Modifier
                .width(300.dp)
                .height(60.dp),
            colors = TextFieldDefaults.textFieldColors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                cursorColor = Color.Black,
                containerColor = Color.White,
                focusedLeadingIconColor = Color.Gray,
                unfocusedLeadingIconColor = Color.Gray,
                focusedLabelColor = Color.Gray,
                unfocusedLabelColor = Color.Black,
                focusedTrailingIconColor = Color.Gray,
                unfocusedTrailingIconColor = Color.Gray
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        //If there is a password error, display error message
        if (errorP) {
            Text(
                text = "Enter Password",
                color = Color.Red,
                modifier = Modifier.padding(end = 100.dp)
            )
        }
        //If there is a password length error, display error message
        if (plength) {
            Text(
                text = "Password must be 6 characters long",
                color = Color.Red,
                modifier = Modifier.padding(end = 100.dp)
            )
        }

        // TextField for password input
        TextField(
            value = password,
            onValueChange = {
                password = it
                plength = it.length < 6
            },
            label = {
                Text(text = "Password")
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_lock_24),
                    contentDescription = null
                )
            },
            trailingIcon = {
                if (password.isNotEmpty()){
                    val visibilityIcon = if (passwordVisibility) {
                        painterResource(id = R.drawable.baseline_visibility_24)
                    }
                    else {
                        painterResource(id = R.drawable.baseline_visibility_off_24)
                    }
                    Icon(
                        painter = visibilityIcon,
                        contentDescription = if (passwordVisibility) "Hide Password" else "Show Password",
                        Modifier.clickable{
                            passwordVisibility = !passwordVisibility
                        }
                    )
                }
            },
            visualTransformation = if (passwordVisibility) {
               VisualTransformation.None
            }
            else {
                 PasswordVisualTransformation()
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Password
            ),
            singleLine = true,
            textStyle = TextStyle(
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            ),
            shape = RoundedCornerShape(50.dp),
            modifier = Modifier
                .width(300.dp)
                .height(60.dp),
            colors = TextFieldDefaults.textFieldColors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                cursorColor = Color.Black,
                containerColor = Color.White,
                focusedLeadingIconColor = Color.Gray,
                unfocusedLeadingIconColor = Color.Gray,
                focusedLabelColor = Color.Gray,
                unfocusedLabelColor = Color.Black,
                focusedTrailingIconColor = Color.Gray,
                unfocusedTrailingIconColor = Color.Gray
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        //Error Text for confirmation password field
        if (errorCP) {
            Text(
                text = "Passwords do not match",
                color = Color.Red,
                modifier = Modifier.padding(end = 100.dp)
            )
        }
        //Error Text for empty confirmation password field
        if (errorC) {
            Text(
                text = "Enter Confirmation Password",
                color = Color.Red,
                modifier = Modifier.padding(end = 100.dp)
            )
        }

        //TextField for confirmation password input
        TextField(
            value = cpassword,
            onValueChange = {
                cpassword = it
            },
            label = {
                Text(text = "Confirm Password")
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_lock_24),
                    contentDescription = null
                )
            },
            trailingIcon = {
                if (cpassword.isNotEmpty()){
                    val visibilityIcon = if (cpasswordVisibility) {
                        painterResource(id = R.drawable.baseline_visibility_24)
                    }
                    else {
                        //CHANGE ICON TO VISIBILITY OFF - UNAVAILABLE
                        painterResource(id = R.drawable.baseline_visibility_off_24)
                    }
                    Icon(
                        painter = visibilityIcon,
                        contentDescription = if (cpasswordVisibility) "Hide Password" else "Show Password",
                        Modifier.clickable{
                            cpasswordVisibility = !cpasswordVisibility
                        }
                    )
                }
            },
            visualTransformation = if (cpasswordVisibility) {
                VisualTransformation.None
            }
            else {
                PasswordVisualTransformation()
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            singleLine = true,
            textStyle = TextStyle(
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            ),
            shape = RoundedCornerShape(50.dp),
            modifier = Modifier
                .width(300.dp)
                .height(60.dp),
            colors = TextFieldDefaults.textFieldColors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                cursorColor = Color.Black,
                containerColor = Color.White,
                focusedLeadingIconColor = Color.Gray,
                unfocusedLeadingIconColor = Color.Gray,
                focusedLabelColor = Color.Gray,
                unfocusedLabelColor = Color.Black,
                focusedTrailingIconColor = Color.Gray,
                unfocusedTrailingIconColor = Color.Gray
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        //Box containing the Sign Up button
        Box (
            modifier = Modifier
                .clip(RoundedCornerShape(50.dp))
                .background(
                    color = Color.White
                )
        ) {
            Button(
                // Signs up new user
                onClick = {
                    if (email.isNotEmpty()){
                        errorE = false
                        if (password.isNotEmpty()){
                            errorP = false
                            if (cpassword.isNotEmpty()) {
                                errorC = false
                                if (password == cpassword) {
                                    errorCP = false
                                    vm.onSignup(email, password)
                                }
                                else {
                                    errorCP = true
                                }
                            }
                            else {
                                errorC = true
                            }
                        }
                        else {
                            errorP = true
                        }
                    }
                    else {
                        errorE = true
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    Color(0xFF0D99FF)
                ),
                modifier = Modifier.width(300.dp)
            ) {
                Text(
                    text = "Sign Up",
                    color = Color.White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            //If user is signed in, navigate to home screen
            if (vm.signedIn.value) {
                //Checks the local database if there is a user that matches the user's info
                val userExists = users.any { it.email == email && it.password == password }
                if (userExists) {
                    //User already exists, navigate to home screen
                    val existingUser = users.find { it.email == email && it.password == password }
                    existingUser?.let { user ->
                        Log.d("Login","User Already exists. User ID = ${user.userId}")
                        navController.navigate("home/${user.userId}")
                    }
                } else {
                    //User does not exist, add a new user to the local database
                    appViewModel.insert(User(email = email, password = password)) { userId ->
                        Log.d("Login","New user inserted. User ID = $userId")
                        navController.navigate("home/$userId")
                    }

                }

            }
            vm.signedIn.value = false
        }

        Spacer(modifier = Modifier.height(10.dp))

        //Text to navigate back to login screen
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.CenterStart)
                .padding(start = 50.dp)
        ) {
            Text(
                text = "Back To Log In",
                color = Color.Gray,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .clickable {
                        navController.navigate(DestinationScreen.Login.route)
                    }

            )
        }

    }

}

