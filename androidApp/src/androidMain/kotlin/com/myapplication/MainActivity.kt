package com.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.rememberCoroutineScope
import com.myapplication.model.MenuItem
import com.myapplication.ui.AppBar
import com.myapplication.ui.DrawerBody
import com.myapplication.ui.DrawerHeader
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApplicationTheme {
                val scaffoldState = rememberScaffoldState()
                val scope = rememberCoroutineScope()
                Scaffold(
                    scaffoldState = scaffoldState,
                    topBar = {
                             AppBar(
                                 onNavigationClick = {
                                     scope.launch {
                                         scaffoldState.drawerState.open()
                                     }
                                 }
                             )
                    },
                    drawerGesturesEnabled =  scaffoldState.drawerState.isOpen,
                    drawerContent = {
                        DrawerHeader()
                        Divider()
                        DrawerBody(
                            items = listOf(
                                MenuItem(
                                    id = "home",
                                    title = "Home",
                                    contentDescription = "Go to home screen",
                                    icon = Icons.Default.Home
                                ),
                                MenuItem(
                                    id = "settings",
                                    title = "Settings",
                                    contentDescription = "Go to settings screen",
                                    icon = Icons.Default.Settings
                                ),
                                MenuItem(
                                    id = "help",
                                    title = "Help",
                                    contentDescription = "Go to help screen",
                                    icon = Icons.Default.Info
                                )
                            ),
                            onItemClick = {
                                println("Clicked on ${it.title}")
                            }
                        )
                    },
                ) {
                    HotelList()
                }
            }
        }
    }
}