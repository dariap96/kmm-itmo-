
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.myapplication.R
import com.myapplication.auth.login.LoginUiState
import com.myapplication.common.components.CustomTextField
import com.myapplication.common.theming.AppTheme
import com.myapplication.common.theming.ButtonHeight
import com.myapplication.common.theming.ExtraLargeSpacing
import com.myapplication.common.theming.LargeSpacing
import com.myapplication.common.theming.MediumSpacing
import com.myapplication.common.theming.SmallSpacing

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    uiState: LoginUiState,
    onLoginChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignIn:() -> Unit
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
            CustomTextField(
                value = uiState.login,
                onValueChange = onLoginChange,
                hint = R.string.username_hint,
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

            Button(
                onClick = {
                   onSignIn()
                },
                modifier = modifier
                    .fillMaxWidth()
                    .height(ButtonHeight),
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 0.dp
                ),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(text = stringResource(id = R.string.login_button_label))
            }
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
                navController.navigate(AppScreen.Hotels.name)
            }
            val test = uiState.authErrorMessage
            println("$test uiState.authErrorMessage onLogin()")
            if (uiState.authErrorMessage?.isNotEmpty() == true) {
                Toast.makeText(context, uiState.authErrorMessage, Toast.LENGTH_SHORT).show()
                //todo: fix that it appears only for the first login attempt
            }
        }
    )
}

@Preview
@Composable
fun LoginScreenPreview() {
    AppTheme {
        LoginScreen(
            rememberNavController(),
            uiState = LoginUiState(),
            onLoginChange = {},
            onPasswordChange = {},
            onSignIn = {}
        )
    }
}
















