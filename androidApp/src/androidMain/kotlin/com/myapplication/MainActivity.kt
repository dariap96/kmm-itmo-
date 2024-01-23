package com.myapplication

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.myapplication.common.theming.AppTheme
import com.myapplication.model.MenuItem
import com.myapplication.navigation.AppNavHost
import com.myapplication.ui.AppBar
import com.myapplication.ui.DrawerBody
import com.myapplication.ui.DrawerHeader
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController: NavHostController = rememberNavController()
            val navStackBackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navStackBackEntry?.destination?.route

            AppTheme {
                val scaffoldState = rememberScaffoldState()
                val scope = rememberCoroutineScope()
                Scaffold(
                    scaffoldState = scaffoldState,
                    topBar = {
                        if (currentDestination !== AppScreen.Login.name) {
                            AppBar(
                                onNavigationClick = {
                                    scope.launch {
                                        scaffoldState.drawerState.open()
                                    }
                                }
                            )
                        }
                    },
                    drawerGesturesEnabled =  scaffoldState.drawerState.isOpen,
                    drawerContent = {
                        DrawerHeader()
                        Divider()
                        DrawerBody(
                            items = listOf(
                                MenuItem(
                                    id = AppScreen.Hotels.name,
                                    title = "Hotels",
                                    contentDescription = "Go to home screen",
                                    icon = Icons.Default.Home
                                ),
                                MenuItem(
                                    id = AppScreen.Managers.name,
                                    title = "Managers",
                                    contentDescription = "Go to managers screen",
                                    icon = Icons.Default.AccountCircle
                                ),
                                MenuItem(
                                    id = AppScreen.Clients.name,
                                    title = "Clients",
                                    contentDescription = "Go to clients screen",
                                    icon = Icons.Default.List
                                ),
                                MenuItem(
                                    id = AppScreen.Requests.name,
                                    title = "Requests",
                                    contentDescription = "Go to requests screen",
                                    icon = Icons.Default.Email
                                )
                            ),
                            onItemClick = {
                                navController.navigate(it.id)
                                scope.launch {
                                    scaffoldState.drawerState.close()
                                }
                            }
                        )
                    },
                ) { innerPadding ->
                    AppNavHost(
                        navHostController = navController,
                        startDestination = AppScreen.Login.name,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

}