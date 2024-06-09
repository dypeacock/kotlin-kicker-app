package com.example.CourseworkApp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.CourseworkApp.R
import com.example.CourseworkApp.data.AppViewModel

@Composable
fun PostsScreen(navController: NavController, appViewModel: AppViewModel, kickNb : Int, date: String, area: String, distance: Int, userId : Int) {  //Need : Kick Number, Total Kicks, Date
    //State variables for managing selected radio button indices and dialog visibility
    var selectedRowIndex by remember { mutableIntStateOf(0) }
    var selectedColumnIndex by remember { mutableIntStateOf(0) }
    val context = LocalContext.current
    val showDialog = remember { mutableStateOf(false) }

    //Main layout for the Posts screen
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFE6E6E6)),
        contentAlignment = Alignment.TopCenter
    ){

        //Posts image
        Box(
            modifier = Modifier
                .size(width = 400.dp, height = 650.dp)
                .offset(y = (-30).dp)
            ,
            contentAlignment = Alignment.TopCenter

        ){
            Image(
                painter = painterResource(id = R.drawable.posts),
                contentDescription = "Posts image",
                contentScale = ContentScale.Crop,
                )
        }

        //Top Text Boxes
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
            ,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            //Kick Number Text Box
            Box(
                modifier = Modifier
                    .width(150.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .background(Color.White)
                    .padding(5.dp)
                ,
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Kick $kickNb")
            }

            //Date Text Box
            Box(
                modifier = Modifier
                    .width(150.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .background(Color.White)
                    .padding(5.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = date)
            }
        }

        //Radio Buttons
        Column {
            val verticalOffsets = listOf(110, 150, 190, 230)
            val horizontalOffsets = listOf(-70, 0, 70)

            repeat(4) { rowIndex ->
                Row(
                    modifier = Modifier
                        .selectableGroup()
                        .offset(y = verticalOffsets[rowIndex].dp)
                ) {
                    repeat(3) { columnIndex ->

                        RadioButton(
                            selected = (selectedRowIndex == rowIndex && selectedColumnIndex == columnIndex),
                            onClick = {
                                selectedRowIndex = rowIndex
                                selectedColumnIndex = columnIndex
                            },
                            modifier = Modifier
                                .semantics { contentDescription = "Localized Description" }
                                .offset(x = horizontalOffsets[columnIndex].dp)
                        )
                    }
                }
            }
        }


        //Exit Dialog for confirmation
        if (showDialog.value) {
            AlertDialog(
                onDismissRequest = {
                    //Dismiss the dialog if the user clicks outside
                    showDialog.value = false
                },
                title = {
                    Text(text = "Abort Session?")
                },
                text = {
                    Text(text = "Are you sure you want to end your session?\nYour session data will not be recorded!")
                },
                confirmButton = {
                    Button(
                        onClick = {
                            //End the session without recording any kicks
                            appViewModel.abortSession()
                            Toast.makeText(context, "Session Aborted", Toast.LENGTH_SHORT).show()

                            //Dismiss the dialog
                            showDialog.value = false
                            navController.navigate("home/$userId")

                        }
                    ) {
                        Text(text = "Yes")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            //Dismiss the dialog
                            showDialog.value = false
                        }
                    ) {
                        Text(text = "No")
                    }
                }
            )
        }


        //Bottom Buttons
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
            ,
            contentAlignment = Alignment.BottomCenter
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 80.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //Posts Screen Buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    //Return Button
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFBDE3FF),
                            contentColor = Color.Black
                        ),
                        onClick = {
                            navController.navigate("pitch/$kickNb/$date/$userId")
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_undo_24),
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier
                                .size(30.dp)
                        )
                    }

                    //Next Button
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF14AE5C),
                        ),
                        onClick = {
                            //Code for determining success of kick
                            val success: Boolean = selectedRowIndex != 3 && selectedColumnIndex == 1

                            Toast.makeText(context, "Kick outcome has been recorded!", Toast.LENGTH_SHORT).show()
                            val incrementedKickNb = kickNb+1
                            appViewModel.recordKick(distance = distance, isSuccess = success, area = area)

                            //Navigating to the next screen
                            navController.navigate("pitch/$incrementedKickNb/$date/$userId")

                        }
                    ) {
                        Text(text = "Confirm")
                    }
                }

            }
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
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFE6E6E6),
                            contentColor = Color.Black
                        ),
                        onClick = {
                            showDialog.value = true
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
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFE6E6E6),
                            contentColor = Color.Black
                        ),
                        onClick = {
                            // Should not be an option to start a new session from within an existing session
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