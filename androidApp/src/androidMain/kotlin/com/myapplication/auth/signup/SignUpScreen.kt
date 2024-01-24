package com.myapplication.auth.signup

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.myapplication.AppScreen
import com.myapplication.common.components.CustomTextField
import com.myapplication.R
import com.myapplication.common.theming.AppTheme
import com.myapplication.common.theming.ButtonHeight
import com.myapplication.common.theming.ExtraLargeSpacing
import com.myapplication.common.theming.MediumSpacing
import com.myapplication.common.theming.LargeSpacing
import com.myapplication.common.theming.SmallSpacing
import com.myapplication.viewmodel.SignUpUiState

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignUpScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    uiState: SignUpUiState,
    onLoginChange: (String) -> Unit,
    onNameChange: (String) -> Unit,
    onSurnameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignUp: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(
                    color = if (isSystemInDarkTheme()) {
                        MaterialTheme.colors.background
                    } else {
                        MaterialTheme.colors.surface
                    }
                )
                .padding(
                    top = ExtraLargeSpacing + LargeSpacing,
                    start = LargeSpacing + MediumSpacing,
                    end = LargeSpacing + MediumSpacing,
                    bottom = LargeSpacing
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(LargeSpacing)
        ) {

            Text(text = "REGISTER MANAGER", style = MaterialTheme.typography.h6)

            Spacer(modifier = Modifier.height(15.dp))

            CustomTextField(
                value = uiState.login,
                onValueChange = onLoginChange,
                hint = R.string.username_hint,
                keyboardActions = KeyboardActions(
                    onDone = {keyboardController?.hide()})
            )

            CustomTextField(
                value = uiState.name,
                onValueChange = onNameChange,
                hint = R.string.name_hint,
                keyboardType = KeyboardType.Text,
                keyboardActions = KeyboardActions(
                    onDone = {keyboardController?.hide()})
            )

            CustomTextField(
                value = uiState.surname,
                onValueChange = onSurnameChange,
                hint = R.string.surname_hint,
                keyboardType = KeyboardType.Text,
                keyboardActions = KeyboardActions(
                    onDone = {keyboardController?.hide()})
            )

            CustomTextField(
                value = uiState.password,
                onValueChange = onPasswordChange,
                hint = R.string.password_hint,
                keyboardType = KeyboardType.Password,
                isPasswordTextField = true,
                keyboardActions = KeyboardActions(
                    onDone = {keyboardController?.hide()})
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                    //todo: implement set role logic
                    uiState.role = "manager"
                    onSignUp()
                },
                modifier = modifier
                    .fillMaxWidth()
                    .height(ButtonHeight),
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 0.dp
                ),
                shape = MaterialTheme.shapes.medium,
                enabled = uiState.name.length > 0
                        && uiState.surname.length > 0
                        && uiState.login.length > 0
                        && uiState.password.length > 0
            ) {
                Text(text = stringResource(id = R.string.signup_button_hint))
            }

//            GoToLogin(modifier) {
//
//            }

        }

        if (uiState.isAuthenticating) {
            CircularProgressIndicator()
        }
    }

    LaunchedEffect(
        key1 = uiState.authenticationSucceed,
        key2 = uiState.authErrorMessage,
        block = {
            if (uiState.authenticationSucceed) {
              //  onNavigateToHome()
            }

            if (uiState.authErrorMessage?.isNotEmpty() == true) {
                Toast.makeText(context, uiState.authErrorMessage, Toast.LENGTH_SHORT).show()
                //todo: fix that it appears only for the first signup attempt
            }
        }
    )
}

@Composable
fun GoToLogin(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier, horizontalArrangement = Arrangement.spacedBy(
            SmallSpacing
        )
    ) {
        Text(text = "Already have an account?", style = MaterialTheme.typography.caption)
        Text(
            text = "Login",
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.primary,
            modifier = modifier.clickable { }
        )
    }
}

@Preview
@Composable
fun SignUpScreenPreview() {
    AppTheme {
        SignUpScreen(
            rememberNavController(),
            uiState = SignUpUiState(),
            onLoginChange = {},
            onNameChange = {},
            onSurnameChange = {},
            onPasswordChange = {},
            onSignUp = {}
        )
    }
}











