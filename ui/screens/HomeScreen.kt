package com.example.CourseworkApp.ui.screens


import com.example.CourseworkApp.R
import android.os.Build
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.CourseworkApp.data.firebase.FbViewModel
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import com.example.CourseworkApp.DestinationScreen
import com.example.CourseworkApp.data.AppViewModel
import com.example.CourseworkApp.data.session.Session
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.ceil

//Function to get the current date
fun getCurrentDate(): String {
    val currentDate = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        LocalDate.now()
    } else {
        TODO("VERSION.SDK_INT < O")
    }
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    return currentDate.format(formatter)
}

//Function to convert a date string to a formatted date with ordinal suffix
fun convertToDateWithOrdinalSuffix(dateString: String): String {
    val formatter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        DateTimeFormatter.ofPattern("dd-MM-yyyy")
    } else {
        TODO("VERSION.SDK_INT < O")
    }
    val date = LocalDate.parse(dateString, formatter)
    val dayOfMonth = date.dayOfMonth
    val formattedDay = getFormattedDayWithSuffix(dayOfMonth)
    val resultFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy")
    return date.format(resultFormatter).replaceFirst(dayOfMonth.toString(), formattedDay)
}

//Function to get the formatted day with ordinal suffix
fun getFormattedDayWithSuffix(day: Int): String {
    return when (day) {
        1, 21, 31 -> "${day}st"
        2, 22 -> "${day}nd"
        3, 23 -> "${day}rd"
        else -> "${day}th"
    }
}

//Composable function to display a session element
@Composable
fun SessionElement(navController: NavController, appViewModel: AppViewModel, session: Session, userId: Int){
    //Collect all kicks LiveData
    val allKicksState = appViewModel.allKicks.observeAsState(initial = emptyList())

    //Filter kicks belonging to the current session
    val sessionKicks = allKicksState.value.filter { it.sessionId == session.sessionId }

    //Calculate successful kicks and total kicks
    val successfulKicks = sessionKicks.count { it.success }
    val totalKicks = sessionKicks.size

    val sessionType = session.sessionType
    val date = session.date

    //Calculate success rate
    val successRate = (successfulKicks.toDouble() / totalKicks) * 100
    val roundedSuccessRate = ceil(successRate).toInt()
    val formattedDate = convertToDateWithOrdinalSuffix(date)

    //Display session element based on session type
    if(sessionType == "Match"){
        //Item for a game session
        Box(
            modifier = Modifier
                .clickable {
                    navController.navigate("statistics/${session.sessionId}/$userId")
                }
                .width(330.dp)
                .clip(RoundedCornerShape(25.dp))
                .background(Color(0xFF1E1E1E))
                .padding(5.dp)
            ,
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                ,
                horizontalArrangement = Arrangement.SpaceBetween,
            ){
                Column(
                    modifier = Modifier
                        .padding(5.dp)
                        .offset(x = 20.dp)

                ){
                    Text(
                        text = formattedDate,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                    )

                    Text(
                        text = "Made $successfulKicks attempted $totalKicks",
                        fontSize = 10.sp,
                        color = Color.White,
                    )
                }


                Box(
                    modifier = Modifier
                        .offset(y = 2.dp)
                        .width(40.dp)
                        .height(40.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .background(Color(0xFF757575))
                        .padding(5.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$roundedSuccessRate%",
                        fontSize = 10.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }

            }

        }
    } else {
        //Item for a training session
        Box(
            modifier = Modifier
                .clickable {
                    navController.navigate("statistics/${session.sessionId}/$userId")
                }
                .width(330.dp)
                .clip(RoundedCornerShape(25.dp))
                .background(Color(0xFF0D99FF))
                .padding(5.dp)
            ,
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                ,
                horizontalArrangement = Arrangement.SpaceBetween,
            ){
                Column(
                    modifier = Modifier
                        .padding(5.dp)
                        .offset(x = 20.dp)

                ){
                    Text(
                        text = formattedDate,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                    )

                    Text(
                        text = "Made $successfulKicks attempted $totalKicks",
                        fontSize = 10.sp,
                        //fontWeight = FontWeight.Bold,
                        color = Color.White,
                    )
                }


                Box(
                    modifier = Modifier
                        .offset(y = 2.dp)
                        .width(40.dp)
                        .height(40.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .background(Color(0xFFBDE3FF))
                        .padding(5.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$roundedSuccessRate%",
                        fontSize = 10.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }

            }

        }
        //
    }

}

@Composable
fun HomeScreen(navController: NavController, vm: FbViewModel, appViewModel: AppViewModel,userId: Int){
    //State to manage dialog visibility
    val showDialog = remember { mutableStateOf(false) }

    //Collect all sessions LiveData
    val allSessionsState = appViewModel.allSessions.observeAsState(initial = emptyList())

    //Filter sessions belonging to the user in reverse order (most recent first)
    val userSessions = allSessionsState.value.filter { it.userId == userId }.reversed()

    //Observe user data
    val userDataState by appViewModel.getUserByID(userId).observeAsState()
    val userEmail = userDataState?.email ?: ""

    //Main layout for the Home screen
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFE6E6E6)),
        contentAlignment = Alignment.TopCenter
    ){
        //User information section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(25.dp)
                .padding(top = 20.dp)
            ,
            verticalAlignment = Alignment.CenterVertically
        ) {
            //User Icon
            Box(
                modifier = Modifier
                    .background(color = Color.White, shape = CircleShape)
                    .size(width = 50.dp, height = 50.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_person_24),
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.Center)
                )
            }

            //Username Text
            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ){
                if (userDataState == null) {
                    //Display a loading indicator
                    Text(
                        text = "Loading...",
                        fontSize = 16.sp,
                        color = Color.Gray,
                    )
                } else {
                    //Display the user email
                    Text(
                        text = userEmail,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                    )
                }

                Text(
                    text = "Kicker, Loughborough University",
                    fontSize = 16.sp,
                    color = Color.Black,
                )

            }

        }

        //Sessions section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(25.dp)
                .padding(top = 100.dp)
                .padding(bottom = 70.dp)
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                //Sessions title with session type indicators
                Text(
                    text = "Sessions",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                )

                Row(
                    modifier = Modifier
                        .offset(x = -15.dp)
                        .offset(y = 5.dp)
                ){
                    //Match session indicator
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50.dp))
                            .background(Color.Black)
                            .padding(5.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Match",
                            fontSize = 10.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    //Training session indicator
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50.dp))
                            .background(Color(0xFF0D99FF))
                            .padding(5.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Training",
                            fontSize = 10.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

            }

            Spacer(modifier = Modifier.height(16.dp))


            //Check if userSessions is empty
            if (userSessions.isEmpty()) {
                //Display text indicating no sessions found
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No sessions found.",
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                }
            } else {
                //Display user sessions
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    items(userSessions.size) { index ->
                        val session = userSessions[index]
                        SessionElement(navController, appViewModel, session, userId)
                    }
                }
            }

        }

        //Dialog for selecting session type
        if (showDialog.value) {
            AlertDialog(
                onDismissRequest = {
                    //Dismiss the dialog if the user clicks outside
                    showDialog.value = false
                },
                title = {
                    Text(text = "Select Session Type:")
                },
                text = {
                    Text(text = "What session do you wish to record?")
                },
                confirmButton = {
                    //Confirm button for Training session
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50.dp))
                            .background(Color(0xFF0D99FF))
                            .clickable {
                                showDialog.value = false

                                val currentDate = getCurrentDate()
                                val kickNb = 1

                                appViewModel.startSession(date = currentDate,type= "Training", userId)
                                Log.d("Home Screen","Session Started")
                                navController.navigate("pitch/$kickNb/$currentDate/$userId")
                            }
                            .padding(5.dp)
                    ) {
                        Text(
                            text = "Training",
                            color = Color.White,
                            modifier = Modifier.padding(5.dp)
                        )
                    }

                },
                dismissButton = {
                    //Dismiss button for Match session
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(25.dp))
                            .background(Color.Black)
                            .clickable {
                                showDialog.value = false

                                val currentDate = getCurrentDate()
                                val kickNb = 1

                                appViewModel.startSession(date = currentDate,type= "Match", userId)
                                Log.d("Home Screen","Session Started")
                                navController.navigate("pitch/$kickNb/$currentDate/$userId")
                            }
                            .padding(5.dp)
                    ) {
                        Text(
                            text = "Match",
                            color = Color.White,
                            modifier = Modifier.padding(5.dp)
                        )
                    }
                }
            )
        }

        //Navbar Buttons
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
                .align(Alignment.BottomCenter)
            ,
            contentAlignment = Alignment.BottomCenter
        ){

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .clip(RoundedCornerShape(50.dp))
                ,
            ){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.White)
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {

                    //Logout Button
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFE6E6E6),
                            contentColor = Color.Black
                        ),
                        onClick = {
                            navController.navigate(DestinationScreen.Login.route)
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_logout_24),
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier
                                .size(30.dp)
                        )
                    }

                    //New Activity Button
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFE6E6E6),
                            contentColor = Color.Black
                        ),
                        onClick = {
                            showDialog.value = true
                        }
                    ) {

                        Icon(
                            painter = painterResource(id = R.drawable.baseline_add_24),
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier
                                .size(30.dp)
                        )

                    }
                }
            }

        }

    }

}