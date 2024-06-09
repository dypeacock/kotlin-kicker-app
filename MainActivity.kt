package com.example.CourseworkApp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.CourseworkApp.data.AppViewModel
import com.example.CourseworkApp.data.firebase.FbViewModel
import com.example.CourseworkApp.ui.screens.HomeScreen
import com.example.CourseworkApp.ui.screens.LoginScreen
import com.example.CourseworkApp.ui.screens.PitchScreen
import com.example.CourseworkApp.ui.screens.PostsScreen
import com.example.CourseworkApp.ui.screens.SignupScreen
import com.example.CourseworkApp.ui.screens.StatisticsScreen
import com.example.CourseworkApp.data.firebase.NotificationMessage
import com.example.CourseworkApp.ui.theme.CourseworkAppTheme
import dagger.hilt.android.AndroidEntryPoint

//Entry point for the application
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //Set status bar and navigation bar colour
            window.statusBarColor = getColor(R.color.black)
            window.navigationBarColor = getColor(R.color.black)

            //Set the content of the activity
            CourseworkAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp()
                }
            }
        }
    }

    //Log lifecycle methods for debugging
    override fun onStart() {
        super.onStart()
        Log.v("Coursework App Methods","onStart");
    }

    override fun onResume() {
        super.onResume()
        Log.v("Coursework App Methods","onResume");
    }

    override fun onPause() {
        super.onPause()
        Log.v("Coursework App Methods","onPause");
    }

    override fun onStop() {
        super.onStop()
        Log.v("Coursework App Methods","onStop");
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.v("Coursework App Methods","onDestroy");
    }

}

//Sealed class defining navigation destinations
sealed class DestinationScreen(val route: String) {
    object Signup: DestinationScreen("signup")
    object Login: DestinationScreen("login")
    object Posts: DestinationScreen("posts/{kickNb}/{date}/{area}/{distance}/{userId}")
    object Pitch: DestinationScreen("pitch/{kickNb}/{date}/{userId}")
    object Statistics: DestinationScreen("statistics/{sessionId}/{userId}")
    object HomeDetail: DestinationScreen("home/{userId}")
}

//Main Composable function for the application
@Composable
fun MyApp(){
    val vm = hiltViewModel<FbViewModel>()
    val appViewModel = hiltViewModel<AppViewModel>()
    val navController = rememberNavController()

    //Display notification messages
    NotificationMessage(vm)

    //Define navigation routes and their corresponding Composables
    NavHost(navController = navController, startDestination = DestinationScreen.Login.route) {
        composable(DestinationScreen.Signup.route){
            SignupScreen(navController, vm, appViewModel)
        }
        composable(DestinationScreen.Login.route){
            LoginScreen(navController, vm, appViewModel)
        }
        composable(
            route = DestinationScreen.Posts.route,
            arguments = listOf(
                navArgument("kickNb") { type = NavType.IntType },
                navArgument("date") { type = NavType.StringType },
                navArgument("area") { type = NavType.StringType },
                navArgument("distance") { type = NavType.IntType },
                navArgument("userId") { type = NavType.IntType }
            )
        ){entry ->
            val kickNb = entry.arguments?.getInt("kickNb")
            val date = entry.arguments?.getString("date")
            val area = entry.arguments?.getString("area")
            val distance = entry.arguments?.getInt("distance")
            val userId = entry.arguments?.getInt("userId")
            if (kickNb != null && date != null && area != null && distance != null && userId != null) {
                PostsScreen(navController, appViewModel, kickNb, date, area, distance, userId)
            }
        }
        composable(
            route = DestinationScreen.Pitch.route,
            arguments = listOf(
                navArgument("kickNb") { type = NavType.IntType },
                navArgument("date") { type = NavType.StringType },
                navArgument("userId") { type = NavType.IntType }
            )
        ){entry ->
            val kickNb = entry.arguments?.getInt("kickNb")
            val date = entry.arguments?.getString("date")
            val userId = entry.arguments?.getInt("userId")
            if (kickNb != null && date != null && userId != null) {
                PitchScreen(navController, appViewModel, kickNb, date, userId)
            }
        }
        composable(
            route = DestinationScreen.Statistics.route,
            arguments = listOf(
                navArgument("sessionId") { type = NavType.IntType },
                navArgument("userId") { type = NavType.IntType }
            )
        ){entry ->
            val sessionId = entry.arguments?.getInt("sessionId")
            val userId = entry.arguments?.getInt("userId")
            if (sessionId != null && userId != null){
                StatisticsScreen(navController, appViewModel, sessionId, userId)
            }
        }
        composable(
            route = DestinationScreen.HomeDetail.route,
            arguments = listOf(
                navArgument("userId") { type = NavType.IntType }
            )
        ) { entry ->
            val userId = entry.arguments?.getInt("userId")
            if (userId != null) {
                HomeScreen(navController, vm, appViewModel, userId)
            }
        }

    }
}
