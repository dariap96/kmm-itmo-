package com.myapplication.manager

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.myapplication.common.components.DefaultImage
import com.myapplication.common.components.DefaultListLoader
import com.myapplication.data.ManagerItem
import com.myapplication.model.AppScreen
import com.myapplication.model.Resource
import com.myapplication.viewmodel.ManagersUiState

private val gradientBrush = Brush.verticalGradient(listOf(Color(0x004B4B4B), Color(0x00000000)))


@Composable
fun ManagerListScreen(
    uiState: ManagersUiState,
    modifier: Modifier = Modifier,
    navHostController: NavHostController
) {
    val status = uiState.status
    val items = uiState.data

    MaterialTheme {
        Scaffold { paddingValues ->
            when (status) {
                Resource.Status.LOADING -> {
                    DefaultListLoader(paddingValues)
                }

                Resource.Status.SUCCESS -> {
                    Column(modifier = Modifier.fillMaxSize()) {
                        LazyColumn(
                            modifier = Modifier
                                .padding(paddingValues)
                                .weight(1f)
                        ) {
                            items(items.size) { index ->
                                ManagerCard(items[index]) {
//                                    navHostController.navigate("${AppScreen.HotelItem.name}/${items.data[index].id}") todo
                                }
                            }
                        }

                        Box(
                            modifier = modifier
                                .align(Alignment.End)
                                .padding(10.dp)
                        ) {
                            Button(
                                onClick = {
                                    navHostController.navigate(AppScreen.CreateManager.name)
                                },
                                modifier = modifier
                                    .fillMaxWidth()
                                    .height(50.dp),
                            ) {
                                Text(text = "Create new manager") // todo string resource
                            }
                        }
                    }
                }

                else -> {
                    Text(text = uiState.error ?: "")
                }
            }
        }
    }
}

@Composable
fun ManagerCard(
    managerItem: ManagerItem,
    onClick: () -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .background(gradientBrush)
            .clickable(onClick = onClick),
        //todo: navigation to hotelItem
        elevation = 12.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        Column {
            Item(managerItem)
        }
    }
}

@Composable
private fun Item(managerItem: ManagerItem) {
    Row(Modifier.padding(end = 16.dp, start = 16.dp, top = 10.dp, bottom = 10.dp)) {
        DefaultImage(
            modifier = Modifier
                .height(100.dp)
                .width(100.dp)
                .padding(end = 16.dp)
                .clip(RoundedCornerShape(16.dp))
        )
        Column {
            Text(
                text = managerItem.manager.name,
                style = TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = managerItem.manager.login,
                style = TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraLight,
                    color = Color.Gray
                )
            )
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Apartment,
                    modifier = Modifier.size(12.dp),
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = managerItem.rooms.size.toString(),
                    style = TextStyle(fontFamily = FontFamily.Monospace),
                    color = MaterialTheme.colors.primary
                )
            }
            Spacer(modifier = Modifier.padding(8.dp))
        }
    }
}