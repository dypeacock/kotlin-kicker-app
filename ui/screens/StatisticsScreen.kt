package com.example.CourseworkApp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.CourseworkApp.R
import kotlin.math.ceil
import android.content.Intent
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import com.example.CourseworkApp.data.AppViewModel

//Creates a UI element representing either a match or training session and includes a sharing icon that allows users to share session information.
@Composable
fun ShareableSessionElement(navController: NavController, sessionType: String, date: String, attempted: Int, made:Int){
    val formattedDate = convertToDateWithOrdinalSuffix(date)
    val successRate = (made.toDouble() / attempted) * 100
    val roundedSuccessRate = ceil(successRate).toInt()

    val context = LocalContext.current

    if(sessionType == "Match"){
        //Match element
        Box(
            modifier = Modifier
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
                        text = "Made $made attempted $attempted",
                        fontSize = 10.sp,
                        color = Color.White,
                    )
                }

                Box(
                    modifier = Modifier
                        .offset(y = 2.dp)
                        .offset(x = 40.dp)
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

                //Sharing icon
                Box(
                    modifier = Modifier
                        .offset(y = 2.dp)
                        .width(40.dp)
                        .height(40.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .background(Color.White)
                        .padding(5.dp)
                        .clickable {
                            val shareIntent = Intent().apply {
                                action = Intent.ACTION_SEND
                                type = "text/plain"
                                putExtra(Intent.EXTRA_SUBJECT, "Sharing Session Info")
                                val shareMessage =
                                    "Match Summary:\nDate: $formattedDate\nAttempted: $attempted\nMade: $made"
                                putExtra(Intent.EXTRA_TEXT, shareMessage)
                            }
                            context.startActivity(
                                Intent.createChooser(
                                    shareIntent,
                                    "Share Session Info"
                                )
                            )
                        }
                    ,
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_share_24),
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier
                            .size(20.dp)
                            .offset(x = (-2).dp)
                    )
                }

            }

        }
    } else {
        //Training element
        Box(
            modifier = Modifier
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
                        text = "Made $made attempted $attempted",
                        fontSize = 10.sp,
                        color = Color.White,
                    )
                }

                //Percentage record
                Box(
                    modifier = Modifier
                        .offset(y = 2.dp)
                        .offset(x = 40.dp)
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

                //Sharing icon
                Box(
                    modifier = Modifier
                        .offset(y = 2.dp)
                        .width(40.dp)
                        .height(40.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .background(Color.White)
                        .padding(5.dp)
                        .clickable {
                            val shareIntent = Intent().apply {
                                action = Intent.ACTION_SEND
                                type = "text/plain"
                                putExtra(Intent.EXTRA_SUBJECT, "Sharing Session Info")
                                val shareMessage =
                                    "Training Summary:\nDate: $formattedDate\nAttempted: $attempted\nMade: $made"
                                putExtra(Intent.EXTRA_TEXT, shareMessage)
                            }
                            context.startActivity(
                                Intent.createChooser(
                                    shareIntent,
                                    "Share Session Info"
                                )
                            )
                        }
                    ,
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_share_24),
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier
                            .size(20.dp)
                            .offset(x = (-2).dp)
                    )
                }

            }

        }
    }
}

//Creates a UI element representing a successful or unsuccessful kick
@Composable
fun KickingElement(success: Boolean, kickNumber: Int, totalKicks : Int, distance : Int, side : String){
    if(success){
        //Successful Kick
        Box(
            modifier = Modifier
                .width(330.dp)
                .clip(RoundedCornerShape(25.dp))
                .background(Color(0xFF14AE5C))
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
                        text = "Kick $kickNumber of $totalKicks",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                    )

                    Text(
                        text = "Distance $distance" +"m - $side",
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
                        .background(Color(0xFFAFF4C6))
                        .padding(5.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_check_24),
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier
                            .size(30.dp)
                    )
                }

            }

        }
    } else {
        //Unsuccessful Kick
        Box(
            modifier = Modifier
                .width(330.dp)
                .clip(RoundedCornerShape(25.dp))
                .background(Color(0xFFF24822))
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
                        text = "Kick $kickNumber of $totalKicks",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                    )

                    Text(
                        text = "Distance $distance" +"m - $side",
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
                        .background(Color(0xFFFFC7C2))
                        .padding(5.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_close_24),
                        contentDescription = null,
                        tint = Color.Red,
                        modifier = Modifier
                            .size(30.dp)
                    )
                }

            }

        }
    }
}

//Creates a progress bar to visualize accuracy or distance statistics and accepts a score parameter to determine the progress
@Composable
fun ShowProgress(score : Int =100){

    val progressFactor by remember(score) {
        mutableFloatStateOf(score*0.01f)
    }

    Row(modifier = Modifier
        .fillMaxWidth()
        .height(15.dp)
        .border(
            width = 4.dp,
            brush = Brush.linearGradient(
                colors = listOf(
                    Color(0xFFB3B3B3),
                    Color(0xFFB3B3B3)
                )
            ),
            shape = RoundedCornerShape(50.dp)
        )
        .clip(
            RoundedCornerShape(
                topStartPercent = 50,
                topEndPercent = 50,
                bottomEndPercent = 50,
                bottomStartPercent = 50
            )
        )
        .background(Color(0xFFB3B3B3)),
        verticalAlignment = Alignment.CenterVertically
    ) {


        Button(
            contentPadding = PaddingValues(1.dp),
            onClick = { },
            modifier = Modifier
                .fillMaxWidth(progressFactor)
                .background(color = Color(0xFF14AE5C)),
            enabled = false,
            elevation = null,
            colors = buttonColors(
                containerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent
            )) {

        }
    }
}

//Displays accuracy statistics, showing the success rate of kicks attempted during the session
@Composable
fun AccuracyStatistic(attempted: Int, made:Int){
    val successRate = (made.toDouble() / attempted) * 100
    val roundedSuccessRate = ceil(successRate).toInt()

    Row(
        modifier = Modifier
            .fillMaxWidth(0.95f)
            .padding(top = 20.dp)
        ,
        verticalAlignment = Alignment.CenterVertically
    ) {
        //Stat Value
        Box(
            modifier = Modifier
                .offset(y = 2.dp)
                .width(40.dp)
                .height(40.dp)
                .clip(RoundedCornerShape(50.dp))
                .background(Color(0xFFB3B3B3))
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


        Spacer(modifier = Modifier.width(16.dp))

        //Stat Description and bar
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ){
            Text(
                text = "Accuracy - $made out of $attempted",
                fontSize = 16.sp,
                color = Color.Black,
            )

            Spacer(modifier = Modifier.height(5.dp))
            ShowProgress(roundedSuccessRate)

        }

    }

}

//Displays the longest kick achieved during the session.
@Composable
fun LongestKickStatistic(distance: Int){

    Row(
        modifier = Modifier
            .fillMaxWidth(0.95f)
            .padding(top = 20.dp)
        ,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Stat Value
        Box(
            modifier = Modifier
                .offset(y = 2.dp)
                .width(40.dp)
                .height(40.dp)
                .clip(RoundedCornerShape(50.dp))
                .background(Color(0xFFB3B3B3))
                .padding(5.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "${distance}m",
                fontSize = 10.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        //Stat Description and bar
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ){
            Text(
                text = "Longest Kick - ${distance}m",
                fontSize = 16.sp,
                color = Color.Black,
            )

            Spacer(modifier = Modifier.height(5.dp))
            ShowProgress(distance)

        }

    }

}

//Displays statistics related to kicks made from the left side of the pitch
@Composable
fun LeftStatistics(made: Int, attempted: Int){
    val successRate = (made.toDouble() / attempted) * 100
    val roundedSuccessRate = ceil(successRate).toInt()

    Row(
        modifier = Modifier
            .fillMaxWidth(0.95f)
            .padding(top = 20.dp)
        ,
        verticalAlignment = Alignment.CenterVertically
    ) {
        //Stat Value
        Box(
            modifier = Modifier
                .offset(y = 2.dp)
                .width(40.dp)
                .height(40.dp)
                .clip(RoundedCornerShape(50.dp))
                .background(Color(0xFFB3B3B3))
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


        Spacer(modifier = Modifier.width(16.dp))

        //Stat Description and bar
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ){
            Text(
                text = "Left of Posts - $made out of $attempted",
                fontSize = 16.sp,
                color = Color.Black,
            )

            Spacer(modifier = Modifier.height(5.dp))
            ShowProgress(roundedSuccessRate)

        }

    }

}

//Displays statistics related to kicks made from the right side of the pitch
@Composable
fun RightStatistics(made: Int, attempted: Int){
    val successRate = (made.toDouble() / attempted) * 100
    val roundedSuccessRate = ceil(successRate).toInt()

    Row(
        modifier = Modifier
            .fillMaxWidth(0.95f)
            .padding(top = 20.dp)
        ,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Stat Value
        Box(
            modifier = Modifier
                .offset(y = 2.dp)
                .width(40.dp)
                .height(40.dp)
                .clip(RoundedCornerShape(50.dp))
                .background(Color(0xFFB3B3B3))
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


        Spacer(modifier = Modifier.width(16.dp))

        //Stat Description and bar
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ){
            Text(
                text = "Right of Posts - $made out of $attempted",
                fontSize = 16.sp,
                color = Color.Black,
            )

            Spacer(modifier = Modifier.height(5.dp))
            ShowProgress(roundedSuccessRate)

        }

    }

}

//The main composable function for the statistics screen, aggregates all the above functions to display session statistics
@Composable
fun StatisticsScreen(navController: NavController, appViewModel: AppViewModel, sessionId: Int, userId : Int){

    //Collect all sessions LiveData
    val allSessionsState = appViewModel.allSessions.observeAsState(initial = emptyList())

    //Get the current session
    val thisSession = allSessionsState.value.filter { it.sessionId == sessionId }[0]

    val sessionType = thisSession.sessionType
    val date = thisSession.date

    //Collect all kicks LiveData
    val allKicksState = appViewModel.allKicks.observeAsState(initial = emptyList())
    //Filter kicks belonging to the current session
    val sessionKicks = allKicksState.value.filter { it.sessionId == thisSession.sessionId }

    //Calculate successful kicks and total kicks
    val successfulKicks = sessionKicks.count { it.success }
    val totalKicks = sessionKicks.size

    //Calculate the longest kick
    val longestKick = sessionKicks.filter { it.success }
        .sortedByDescending { it.distance }
        .firstOrNull()?.distance ?: 0

    //Calculate successful left kicks and total kicks
    val successfulLeftKicks = sessionKicks.filter { it.area == "Left" }.filter { it.success }.count()
    val totalLeftKicks = sessionKicks.filter { it.area == "Left" }.count()

    //Calculate successful right kicks and total kicks
    val successfulRightKicks = sessionKicks.filter { it.area == "Right" }.filter { it.success }.count()
    val totalRightKicks = sessionKicks.filter { it.area == "Right" }.count()

    val showDialog = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFE6E6E6)),
        contentAlignment = Alignment.TopCenter
    ){

        //Training Summary
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(25.dp)
                .padding(top = 50.dp)
                .padding(bottom = 70.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (sessionType == "Training"){
                Text(
                    text = "Training Summary",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                )
            } else {
                Text(
                    text = "Match Summary",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            ShareableSessionElement(navController, sessionType, date, totalKicks, successfulKicks)

            AccuracyStatistic(totalKicks, successfulKicks)
            Spacer(modifier = Modifier.height(10.dp))
            LongestKickStatistic(longestKick)
            Spacer(modifier = Modifier.height(10.dp))
            LeftStatistics(successfulLeftKicks,totalLeftKicks)
            Spacer(modifier = Modifier.height(10.dp))
            RightStatistics(successfulRightKicks,totalRightKicks)

            Spacer(modifier = Modifier.height(30.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {

                items (sessionKicks.size){index ->
                    val kick = sessionKicks[index]
                    KickingElement(
                        success = kick.success,
                        kickNumber = index +1,
                        totalKicks = sessionKicks.size,
                        distance = kick.distance,
                        side = kick.area
                    )
                }

            }

        }


        //Dialog for Match vs Training
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
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50.dp))
                            .background(Color(0xFF0D99FF))
                            .clickable {
                                showDialog.value = false

                                val currentDate = getCurrentDate()
                                val kickNb = 1

                                appViewModel.startSession(
                                    date = currentDate,
                                    type = "Training",
                                    userId
                                )
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
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(25.dp))
                            .background(Color.Black)
                            .clickable {
                                showDialog.value = false

                                val currentDate = getCurrentDate()
                                val kickNb = 1

                                appViewModel.startSession(
                                    date = currentDate,
                                    type = "Match",
                                    userId
                                )
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

                    //Home Button
                    Button(
                        colors = buttonColors(
                            containerColor = Color(0xFFE6E6E6),
                            contentColor = Color.Black
                        ),
                        onClick = {
                            navController.navigate("home/$userId")
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_home_filled_24),
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier
                                .size(30.dp)
                        )
                    }

                    //New Activity Button
                    Button(
                        colors = buttonColors(
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