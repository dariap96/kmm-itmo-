package com.myapplication.navigation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.myapplication.AppScreen
import com.myapplication.HotelList
import com.myapplication.auth.login.Login

@Composable
fun AppNavHost(
    navHostController: NavHostController,
    startDestination: String,
    modifier: Modifier = Modifier
) {

    NavHost(
        navController = navHostController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = AppScreen.Hotels.name) {
            HotelList()
        }

        composable(route = AppScreen.Managers.name) {
            Text(text = "Managers List todo") // todo
        }

        composable(route = AppScreen.Login.name) {
            Login(navHostController) // todo
        }

        composable(route = AppScreen.Clients.name) {
            Text(text = "Clients Screen todo") // todo
        }

        composable(route = AppScreen.Requests.name) {
            Text(text = "Requests Screen todo") // todo
        }
    }
}