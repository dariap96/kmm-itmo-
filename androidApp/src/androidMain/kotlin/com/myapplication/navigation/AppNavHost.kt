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
import com.myapplication.hotelItem.HotelList
import com.myapplication.auth.login.Login
import com.myapplication.auth.signup.CreateManager
import com.myapplication.hotelItem.HotelItem
import com.myapplication.home.CreateHotelScreen
import com.myapplication.hotelItem.CreateRoomScreen
import com.myapplication.manager.ManagerList
import com.myapplication.request.RequestList
import com.myapplication.roomItem.RoomItem

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

        composable(route = "${AppScreen.CreateRoom.name}/{hotelId}", arguments = listOf(navArgument("hotelId") {
            type = NavType.IntType
        })) {
            val id = requireNotNull(it.arguments).getInt("hotelId")
            CreateRoomScreen(navHostController, id)
        }

        composable(route = AppScreen.Managers.name) {
            ManagerList(navHostController)
        }

        composable(route = AppScreen.CreateManager.name) {
            CreateManager(navHostController)
        }

        composable(route = AppScreen.Login.name) {
            Login(navHostController)
        }

        composable(route = AppScreen.SignUp.name) {
            CreateManager(navHostController)
        }

        composable(
            route = "${AppScreen.HotelItem.name}/{hotelId}",
            arguments = listOf(navArgument("hotelId") {
                type = NavType.IntType
            })
        ) {
            val id = requireNotNull(it.arguments).getInt("hotelId")
            HotelItem(navHostController, id)
        }

        composable(
            route = "${AppScreen.RoomItem.name}/{roomId}",
            arguments = listOf(navArgument("roomId") {
                type = NavType.IntType
            })
        ) {
            val id = requireNotNull(it.arguments).getInt("roomId")
            RoomItem(navHostController, id)
        }

        composable(route = AppScreen.Clients.name) {
            Text(text = "Clients Screen todo") // todo
        }

        composable(route = AppScreen.Requests.name) {
            RequestList(navHostController)
        }
    }
}