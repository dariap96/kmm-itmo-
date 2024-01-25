package com.myapplication.roomItem

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.myapplication.common.components.DefaultImage
import com.myapplication.R
import com.myapplication.common.components.NonAuthCustomTextField
import com.myapplication.common.theming.AppTheme
import com.myapplication.common.theming.Blue
import com.myapplication.common.theming.ButtonHeight
import com.myapplication.data.ManagerItem
import com.myapplication.viewmodel.RoomItemUiState

@Composable
fun RoomItemScreen(
    navController: NavHostController,
    uiState: RoomItemUiState,
    onManagerChange: (ManagerItem) -> Unit,
    onPriceChange: (String) -> Unit,
    onUpdate: () -> Unit,
    onEditButtonClick: () -> Unit
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    AppTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .clip(RoundedCornerShape(CornerSize(16.dp)))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .verticalScroll(scrollState)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "ROOM #${uiState.number}", style = MaterialTheme.typography.h6)
                }
                Spacer(modifier = Modifier.height(20.dp))
                if (uiState.isAbleToEdit) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Spacer(modifier = Modifier.width(20.dp))
                        EditRoomDataButton(onEditButtonClick, uiState)
                    }
                }

                Text(text = "VIP: ${uiState.isVip}", style = MaterialTheme.typography.h6)
                Spacer(modifier = Modifier.height(16.dp))
                DefaultImage(
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(CornerSize(8.dp)))
                )
                if (uiState.isEditMode) {
                    Spacer(modifier = Modifier.height(16.dp))
                } else {
                    Spacer(modifier = Modifier.height(32.dp))
                }
                Text(text = "ROOM DETAILS", style = MaterialTheme.typography.h6)
                Spacer(modifier = Modifier.height(16.dp))
                RoomItemDetails(uiState, onManagerChange, onPriceChange)
                Spacer(modifier = Modifier.height(30.dp))
                if (uiState.isEditMode) {
                    UpdateRoomDataButton(onUpdate, uiState)
                }
            }
        }
    }

    LaunchedEffect(
        key1 = uiState.updateSucceed,
        key2 = uiState.updateErrorMessage,
        block = {
            if (uiState.updateSucceed) {
                Toast.makeText(context, "room data was updated", Toast.LENGTH_SHORT).show()
            }

            if (uiState.updateErrorMessage?.isNotEmpty() == true) {
                Toast.makeText(context, uiState.updateErrorMessage, Toast.LENGTH_SHORT).show()
                //todo: fix that it appears only for the first login attempt
            }
        }
    )
}

@Composable
fun UpdateRoomDataButton(onUpdate: () -> Unit, uiState: RoomItemUiState) {
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
        enabled = uiState.isEditMode
    ) {
        Text(text = "Update Room Data", color = Color.White)
    }
}

@Composable
fun EditRoomDataButton(onEditButtonClick: () -> Unit, uiState: RoomItemUiState) {
    Button(
        onClick = {
            onEditButtonClick()
        },
        modifier = Modifier
            .width(40.dp)
            .height(40.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Blue
        ),
        shape = RoundedCornerShape(100),
        enabled = !uiState.isEditMode,
        contentPadding = PaddingValues(0.dp)
    ) {
        if (uiState.isEditMode) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .width(20.dp)
                    .height(20.dp)
            )
        } else {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .width(20.dp)
                    .height(20.dp)
            )
        }
    }
}

@Composable
fun RoomItemDetails(
    uiState: RoomItemUiState,
    onManagerChange: (ManagerItem) -> Unit,
    onPriceChange:  (String) -> Unit
) {
    val scrollState = rememberScrollState()
    val expanded = remember { mutableStateOf(false) }

    Column {
        Text(text = "Capacity: ${uiState.capacity}")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(
                    state = scrollState,
                ),
        ) {

        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Floor: ${uiState.floor}")
        Spacer(modifier = Modifier.height(10.dp))
        if (uiState.isEditMode) {
            Text(text = "Price: ${uiState.managerInfoId}")
            Spacer(modifier = Modifier.height(16.dp))
            NonAuthCustomTextField(
                hint = R.string.room_price_hint,
                value = uiState.price.toString(),
                onValueChange = onPriceChange
            )
        } else {
            Row {
                Text(text = "Price: ")
                Text(text = "${uiState.price}$", color = MaterialTheme.colors.primary)
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        if (uiState.isEditMode) {
            Box(
                modifier = Modifier
                    .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
                    .padding(2.dp)
                    .clickable { expanded.value = true }
            ){
                Row {
                    Text(
                        text = "Please select manager assignee",
                        Modifier.clickable { expanded.value = true })
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = "Dropdown Arrow"
                    )
                }
                DropdownMenu(
                    expanded = expanded.value,
                    onDismissRequest = { expanded.value = false }
                ) {
                    uiState.managerInfoList.forEach { managerInfo ->
                        DropdownMenuItem(onClick = {
                            onManagerChange(managerInfo)
                            expanded.value = false
                        }) {
                            Text(text = managerInfo.manager.login)
                        }
                    }
                }
            }
        } else {
            Text(text = "Current Manager Assigned:")
            Text(text = "${uiState.managerLogin}", style = MaterialTheme.typography.h6)
            Text("${uiState.managerName} ${uiState.managerSurname}")
        }
    }
}

@Preview
@Composable
fun HotelItemPreview() {
    AppTheme {
        val roomUIState = RoomItemUiState(
        )
        RoomItemScreen(
            rememberNavController(),
            uiState = roomUIState,
            onManagerChange = {},
            onPriceChange = {},
            onUpdate = {},
            onEditButtonClick = {}
        )
    }
}
