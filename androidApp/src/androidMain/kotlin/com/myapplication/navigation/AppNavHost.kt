package com.myapplication.navigation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.myapplication.model.AppScreen
import com.myapplication.HotelList
import com.myapplication.auth.login.Login
import com.myapplication.auth.signup.SignUp
import com.myapplication.hotelItem.HotelItem
import com.myapplication.home.CreateHotelScreen

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
            HotelList(navHostController)
        }
        composable(route = AppScreen.CreateHotel.name) {
            CreateHotelScreen(navHostController)
        }

        composable(route = AppScreen.Managers.name) {
            Text(text = "Managers List todo") // todo
        }

        composable(route = AppScreen.Login.name) {
            Login(navHostController)
        }

        composable(route = AppScreen.SignUp.name) {
            SignUp(navHostController)
        }

        composable(route = "${AppScreen.HotelItem.name}/{hotelId}", arguments = listOf(navArgument("hotelId") {
            type = NavType.IntType
        })) {
            val id = requireNotNull(it.arguments).getInt("hotelId")
            HotelItem(navHostController, id)
        }

        composable(route = AppScreen.Clients.name) {
            Text(text = "Clients Screen todo") // todo
        }

        composable(route = AppScreen.Requests.name) {
            Text(text = "Requests Screen todo") // todo
        }
    }
}