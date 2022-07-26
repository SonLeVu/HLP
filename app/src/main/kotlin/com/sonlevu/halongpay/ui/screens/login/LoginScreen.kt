package com.sonlevu.halongpay.ui.screens.login

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sonlevu.halongpay.R
import com.sonlevu.halongpay.ui.dialogs.ConfirmationDialog
import com.sonlevu.halongpay.ui.theme.HalongpayTheme
import com.sonlevu.halongpay.ui.theme.PrimaryColorYellow
import com.sonlevu.halongpay.utils.ErrorResult
import com.sonlevu.halongpay.utils.LoadingResult
import com.sonlevu.halongpay.utils.NetworkUtil
import com.sonlevu.halongpay.utils.SuccessResult
import kotlinx.coroutines.launch

@Composable
fun MainLoginScreen(scaffoldState: ScaffoldState,
                    signUp: () -> Unit,
                    paddingValues: PaddingValues) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        color = MaterialTheme.colorScheme.background
    ) {
        val scope = rememberCoroutineScope()
        val context = LocalContext.current
        val showMessage: (Int) -> Unit = { message ->
            val strMessage = context.getString(message)
            scope.launch {
                scaffoldState.snackbarHostState.showSnackbar(strMessage)
            }
        }
        val viewModel: LoginViewModel = viewModel()

        val loginResult by viewModel.loginResult.collectAsState()
        loginResult.also {
            when (it) {
                is ErrorResult -> showMessage(it.message!!)
                is SuccessResult -> {
                    LaunchedEffect(Unit) {
                        //Navigate to Main Payment Screen
                    }
                }
                else -> {}
            }
        }

        LoginScreenContent(
            login = viewModel::login,
            signUp = signUp,
            isLoadingValue = loginResult is LoadingResult || loginResult is SuccessResult
        )
    }

}


@Composable
fun LoginScreenContent(
    login: (login: String, password: String) -> Unit = { _, _ -> },
    signUp: () -> Unit = {},
    isLoadingValue: Boolean = false,
) = ConstraintLayout(
    modifier = Modifier.fillMaxSize(),
) {

    var loginInput by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(
            TextFieldValue()
        )
    }
    var passwordInput by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(
            TextFieldValue()
        )
    }

    var isLoginInputError by remember { mutableStateOf(false) }
    var isPasswordInputError by remember { mutableStateOf(false) }

    val (logo, loginForm, button) = createRefs()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .constrainAs(logo) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(loginForm.top)
            }
            .imePadding()
            .padding(bottom = 18.dp)
    ) {
        Image(
            painter = painterResource(R.mipmap.ic_launcher_adaptive_fore),
            contentDescription = null,
            modifier = Modifier
                .size(130.dp)
                .padding(bottom = 8.dp)
        )

        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.headlineSmall,
        )
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .constrainAs(loginForm) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
            .imePadding()
    ) {
        LoginTextField(
            value = loginInput,
            labelId = R.string.login_username,
            onValueChange = {
                isLoginInputError = false
                loginInput = it
            },
            isError = isLoginInputError
        )

        LoginTextField(
            value = passwordInput,
            labelId = R.string.login_password,
            onValueChange = {
                isPasswordInputError = false
                passwordInput = it
            },
            visualTransformation = PasswordVisualTransformation(),
            keyboardType = KeyboardType.Password,
            isError = isPasswordInputError
        )
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.constrainAs(button) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(loginForm.bottom, 24.dp)
        }
    ) {
        val loginAction = {
            login(
                loginInput.text.trim(),
                passwordInput.text.trim()
            )
        }

        var isAlertVisible by remember { mutableStateOf(false) }

        if (isAlertVisible) {
            ConfirmationDialog(
                title = stringResource(R.string.no_internet),
                text = stringResource(R.string.please_go_online),
                onConfirm = {
                    isAlertVisible = false
                    loginAction()
                },
                onDismiss = { isAlertVisible = false },
                iconId = R.drawable.ic_settings
            )
        }

        if (isLoadingValue) {
            CircularProgressIndicator(
                modifier = Modifier.size(48.dp),
                color = MaterialTheme.colorScheme.primary
            )
        } else {
            val context = LocalContext.current
            val onClick = {
                isLoginInputError = loginInput.text.isBlank()
                isPasswordInputError = passwordInput.text.isBlank()
                if (!(isLoginInputError || isPasswordInputError)) {
                    if (!NetworkUtil.isOnline(context)) {
                        isAlertVisible = true
                    } else {
                        loginAction()
                    }
                }
            }

            Button(
                onClick = {
                    onClick()
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = PrimaryColorYellow),
                shape = RoundedCornerShape(20.dp),
                contentPadding = PaddingValues(horizontal = 40.dp)
            ) {
                Text(stringResource(R.string.login_continue), color = Color.White)
            }

            Spacer(Modifier.height(9.dp))

            Text(stringResource(R.string.sign_up_hint))
            Spacer(Modifier.height(3.dp))
            OutlinedButton(
                border = BorderStroke(1.dp, PrimaryColorYellow),
                contentPadding = PaddingValues(horizontal = 22.dp),
                colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.White),
                shape = RoundedCornerShape(20.dp),
                onClick = {
                    signUp()
                }
            ) {
                Text(stringResource(R.string.sign_up), color = PrimaryColorYellow)
            }
        }
    }
}

@Composable
fun LoginTextField(
    value: TextFieldValue,
    @StringRes labelId: Int,
    onValueChange: (TextFieldValue) -> Unit,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardType: KeyboardType = KeyboardType.Text,
    isError: Boolean = false
) {
    val focusManager = LocalFocusManager.current

    val textStyle =
        MaterialTheme.typography.titleMedium.merge(TextStyle(fontWeight = FontWeight.Normal))
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp)
            .padding(bottom = 6.dp),
        textStyle = textStyle,
        singleLine = true,
        label = { Text(text = stringResource(labelId), style = textStyle) },
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        isError = isError,
        shape = MaterialTheme.shapes.small,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = androidx.compose.material3.LocalContentColor.current.copy(LocalContentAlpha.current),
            cursorColor = MaterialTheme.colorScheme.primary,
            errorCursorColor = MaterialTheme.colorScheme.error,
            focusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = ContentAlpha.high),
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = ContentAlpha.disabled),
            errorBorderColor = MaterialTheme.colorScheme.error,
            leadingIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = TextFieldDefaults.IconOpacity),
            trailingIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = TextFieldDefaults.IconOpacity),
            errorTrailingIconColor = MaterialTheme.colorScheme.error,
            focusedLabelColor = MaterialTheme.colorScheme.primary.copy(alpha = ContentAlpha.high),
            unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(ContentAlpha.medium),
            errorLabelColor = MaterialTheme.colorScheme.error,
            placeholderColor = MaterialTheme.colorScheme.onSurface.copy(ContentAlpha.medium),
        )
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun MainLoginScreenPreview() = HalongpayTheme {
    LoginScreenContent()
}