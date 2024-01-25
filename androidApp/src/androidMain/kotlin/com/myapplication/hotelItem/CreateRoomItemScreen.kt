package com.myapplication.hotelItem

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.myapplication.R
import com.myapplication.common.components.CustomTextField
import com.myapplication.common.theming.ButtonHeight
import com.myapplication.common.theming.ExtraLargeSpacing
import com.myapplication.common.theming.LargeSpacing
import com.myapplication.common.theming.MediumSpacing
import com.myapplication.model.AppScreen
import com.myapplication.viewmodel.CreateRoomViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreateRoomScreen(
    navHostController: NavHostController,
    hotelId: Int,
    modifier: Modifier = Modifier
) {
    val createRoomViewModel = koinViewModel<CreateRoomViewModel>()
    val state = createRoomViewModel.createRoomState.collectAsState().value
    val context = LocalContext.current

    Box(modifier = modifier.fillMaxSize().background(MaterialTheme.colors.surface), contentAlignment = Alignment.Center) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(
                    top = ExtraLargeSpacing + LargeSpacing,
                    start = LargeSpacing + MediumSpacing,
                    end = LargeSpacing + MediumSpacing,
                    bottom = LargeSpacing
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(LargeSpacing)
        ) {

            CustomTextField(
                value = state.number,
                onValueChange = createRoomViewModel::updateNumber,
                hint = R.string.room_number_hint,
            )

            CustomTextField(
                value = state.capacity,
                onValueChange = createRoomViewModel::updateCapacity,
                hint = R.string.room_capacity_hint,
                keyboardType = KeyboardType.Number
            )

            CustomTextField(
                value = state.floor,
                onValueChange = createRoomViewModel::updateFloor,
                hint = R.string.floor_hint,
                keyboardType = KeyboardType.Number
            )

            CustomTextField(
                value = state.price,
                onValueChange = createRoomViewModel::updatePrice,
                hint = R.string.room_price_hint,
                keyboardType = KeyboardType.Decimal
            )

            CustomTextField(
                value = state.isVip,
                onValueChange = createRoomViewModel::updateIsVip,
                hint = R.string.is_vip_hint,
                keyboardType = KeyboardType.Text
            )
            //add manager dropdown


            Button(
                onClick = {
                    createRoomViewModel.createRoom(hotelId)
                },
                modifier = modifier
                    .fillMaxWidth()
                    .height(ButtonHeight),
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 0.dp
                ),
                shape = MaterialTheme.shapes.medium,
                enabled = state.isVip.isNotEmpty()
                        && state.capacity.isNotEmpty()
                        && state.floor.isNotEmpty()
                        && state.number.isNotEmpty()
                        && state.price.isNotEmpty()
            ) {
                Text(text = "Create new room")
            }

        }

        LaunchedEffect(
            key1 = state.roomCreated,
            block = {
                if (state.roomCreated) {
                    Toast.makeText(context, "new room created", Toast.LENGTH_SHORT).show()
                    navHostController.navigate("${AppScreen.HotelItem.name}/${hotelId}")
                }
            }
        )
    }
}