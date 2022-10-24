package io.arjuningole.gpt

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.arjuningole.gpt.home.HomeScreen
import io.arjuningole.gpt.output.OutputScreen
import io.arjuningole.gpt.services.story

@Composable
fun SetupNavGraph(
    navController : NavHostController
){
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route)
    {
        composable(
            Screen.Home.route
        ){
            HomeScreen(navController = navController)
        }
        composable(
            Screen.Output.route
        ){
            OutputScreen(navController)
        }
    }
}