package com.myapplication.home

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
import com.myapplication.viewmodel.CreateHotelViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreateHotelScreen(
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {
    val createHotelViewModel = koinViewModel<CreateHotelViewModel>()
    val state = createHotelViewModel.createHotelState.collectAsState().value
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
                value = state.name,
                onValueChange = createHotelViewModel::changeName,
                hint = R.string.name_hint,
            )

            CustomTextField(
                value = state.stageCount,
                onValueChange = { value -> createHotelViewModel.changeStageCount(value)},
                hint = R.string.stage_count_hint,
                keyboardType = KeyboardType.Decimal
            )

            Button(
                onClick = {
                    createHotelViewModel.createHotel()
                },
                modifier = modifier
                    .fillMaxWidth()
                    .height(ButtonHeight),
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 0.dp
                ),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(text = "Create hotel")
            }

//            GoToLogin(modifier) {
//
//            }

        }

        LaunchedEffect(
            key1 = state.hotelCreated,
            block = {
                if (state.hotelCreated) {
                    Toast.makeText(context, "new hotel created", Toast.LENGTH_SHORT).show()
                    navHostController.navigate(AppScreen.Hotels.name)
                }
            }
        )
    }
}