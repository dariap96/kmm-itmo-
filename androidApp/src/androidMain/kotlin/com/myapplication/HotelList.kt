package com.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.myapplication.data.hotel.HotelItem
import com.myapplication.model.Resource
import com.myapplication.viewmodel.HotelViewModel
import org.koin.androidx.compose.koinViewModel

private val gradientBrush = Brush.verticalGradient(listOf(Color(0x004B4B4B), Color(0x00000000)))

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HotelCard(
    hotelIem: HotelItem,
    navController: NavHostController
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .background(gradientBrush)
            .clickable {  },
        //todo: navigation to hotelItem
        elevation = 12.dp,
        shape = RoundedCornerShape(10.dp)
     ) {
        Column {
            Item(hotelIem)
        }
    }
}

@Composable
fun HotelList(navController: NavHostController) {
    val hotelViewModel = koinViewModel<HotelViewModel>()
    val items by hotelViewModel.items.collectAsState()

    MaterialTheme {
        Scaffold(content = { paddingValues ->
            if (items.status == Resource.Status.LOADING) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .width(64.dp)
                            .padding(paddingValues),
                        color = MaterialTheme.colors.primary,
                    )
                }
            } else if (items.status == Resource.Status.SUCCESS) {
                LazyColumn(modifier = Modifier.padding(paddingValues)) {
                    items(items.data.size) { index ->
                        HotelCard(items.data[index], navController)
                    }
                }
            } else {
                Text(text = items.error ?: "")
            }
        })
    }
}

@Composable
private fun Item(hotelItem: HotelItem) {
    Column(Modifier.padding(end = 16.dp, start = 16.dp, top = 10.dp)) {
        Text(text = hotelItem.name, style = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 18.sp, fontWeight = FontWeight.Bold))
        Text(text = "Saint-Petersburg", style = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 14.sp, fontWeight = FontWeight.ExtraLight, color = Color.Gray))
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Star,
                modifier = Modifier.size(12.dp),
                contentDescription = null,
                tint = MaterialTheme.colors.primary
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = hotelItem.rating?.toString() ?: "0",
                style = TextStyle(fontFamily = FontFamily.Monospace),
                color = MaterialTheme.colors.primary
            )
        }
        Spacer(modifier = Modifier.padding(8.dp))
    }
}