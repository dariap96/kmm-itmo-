package com.myapplication.hotelItem

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.myapplication.common.theming.AppTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.myapplication.R
import com.myapplication.common.components.NonAuthCustomTextField
import com.myapplication.common.theming.Blue
import com.myapplication.common.theming.ButtonHeight
import com.myapplication.viewmodel.HotelItemUiState

@Composable
fun HotelItemScreen (
    navController: NavHostController,
    uiState: HotelItemUiState,
    onNameChange: (String) -> Unit,
    onStageCountChange: (String) -> Unit,
    onUpdate:() -> Unit
) {
    val context = LocalContext.current

    AppTheme {

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .clip(RoundedCornerShape(CornerSize(16.dp)))
                .clickable { }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp, horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "HOTEL", style = MaterialTheme.typography.h6)
                    uiState.rating.let { rating ->
                        if (rating != null) {
                            HotelItemRating(rating)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                if (uiState.isAbleToEdit) {
                    NonAuthCustomTextField(
                        hint = R.string.hotel_name_hint,
                        value = uiState.name,
                        onValueChange = onNameChange
                    )
                } else {
                    Text(text = "Hotel name: ${uiState.name}")
                }
                Spacer(modifier = Modifier.height(16.dp))
                HotelItemImage()
                Spacer(modifier = Modifier.height(16.dp))
                HotelItemDetails(uiState, onStageCountChange)
                Spacer(modifier = Modifier.height(30.dp))
                UpdateHotelDataButton(onUpdate, uiState)
            }
        }
    }

    LaunchedEffect(
        key1 = uiState.updateSucceed,
        key2 = uiState.updateErrorMessage,
        block = {
            if (uiState.updateSucceed) {
                Toast.makeText(context, "hotel data was updated", Toast.LENGTH_SHORT).show()
            }

            if (uiState.updateErrorMessage?.isNotEmpty() == true) {
                Toast.makeText(context, uiState.updateErrorMessage, Toast.LENGTH_SHORT).show()
                //todo: fix that it appears only for the first login attempt
            }
        }
    )
}

@Composable
fun UpdateHotelDataButton(onUpdate:() -> Unit, uiState: HotelItemUiState) {
    Button(
        onClick = {
            onUpdate()
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(ButtonHeight),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Blue
        ),
        enabled = uiState.isUpdateButtonActive
    ) {
        Text(text = "Update Hotel Data", color = Color.White)
    }
}

@Composable
fun HotelItemImage() {
    Image(
        painter = painterResource(id = R.drawable.ic_launcher_background),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(CornerSize(8.dp)))
    )
}

@Composable
fun HotelItemDetails(uiState: HotelItemUiState, onStageCountChange: (String) -> Unit) {
    Column {
        Text(text = "Rooms: ${uiState.rooms.size}")
        Spacer(modifier = Modifier.height(10.dp))
        if (!uiState.isAbleToEdit) {
            NonAuthCustomTextField(
                hint = R.string.stages_hint,
                value = uiState.stages,
                onValueChange = onStageCountChange
            )
        } else {
            Text(text = "Stages: ${uiState.stages.length}")
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Director Info ID: ${uiState.directorInfoId}")
    }
}

@Composable
fun HotelItemRating(rating: Double) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        repeat(5) { index ->
            val starIcon = if (index < rating) {
                Icons.Filled.Star
            } else {
                Icons.Outlined.Clear
            }
            Icon(
                imageVector = starIcon,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Preview
@Composable
fun HotelItemPreview() {
    AppTheme {
        val hotelUIState = HotelItemUiState(
            name = "Sample Hotel",
            rooms = listOf(),
            stages = "3",
            directorInfoId = 123,
            rating = 4.0
        )
        HotelItemScreen(
            rememberNavController(),
            uiState = HotelItemUiState(),
            onNameChange = {},
            onStageCountChange = {},
            onUpdate = {}
        )
    }
}