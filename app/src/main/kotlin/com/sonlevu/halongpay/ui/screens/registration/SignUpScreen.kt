package com.sonlevu.halongpay.ui.screens.registration

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sonlevu.halongpay.R
import com.sonlevu.halongpay.ui.theme.PrimaryColorYellow
import com.sonlevu.halongpay.ui.utils.HyperlinkText

@Composable
fun RegistrationScreen(viewModel: SignUpViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
    ) {
        Spacer(Modifier.size(2.dp))
        Text(text = stringResource(R.string.sign_up_instruction))
        Spacer(Modifier.size(34.dp))

        RegistrationField(
            isError = viewModel.nameErrorState.value,
            errorMess = stringResource(id = R.string.error_required),
            onValueChanged = {
                if (viewModel.nameErrorState.value) {
                    viewModel.nameErrorState.value = false
                }
                viewModel.name.value = it
            },
            stringRes = R.string.regis_fullname,
            textFieldValue = viewModel.name.value
        )
        RegistrationField(
            isError = viewModel.emailErrorState.value,
            errorMess = stringResource(id = R.string.error_required),
            onValueChanged = {
                if (viewModel.emailErrorState.value) {
                    viewModel.emailErrorState.value = false
                }
                viewModel.email.value = it
            },
            stringRes = R.string.regis_email,
            textFieldValue = viewModel.email.value
        )
        RegistrationField(
            isError = false,
            errorMess = stringResource(id = R.string.error_required),
            onValueChanged = {
                viewModel.mobileNo.value = it
            },
            stringRes = R.string.regis_mobile,
            textFieldValue = viewModel.email.value,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                autoCorrect = false
            )
        )

        val passwordVisibility = remember { mutableStateOf(true) }
        val cPasswordVisibility = remember { mutableStateOf(true) }

        RegistrationField(
            isError = viewModel.passwordErrorState.value,
            errorMess = stringResource(id = R.string.error_required),
            onValueChanged = {
                if (viewModel.passwordErrorState.value) {
                    viewModel.passwordErrorState.value = false
                }
                viewModel.password.value = it
            },
            stringRes = R.string.regis_password,
            textFieldValue = viewModel.password.value,
            isEnableTrailingIcon = true,
            isHidingPassword = passwordVisibility.value,
            onTrailingIconClicked = {
                passwordVisibility.value = !passwordVisibility.value
            }
        )

        RegistrationField(
            isError = viewModel.confirmPasswordErrorState.value,
            errorMess = if (viewModel.confirmPassword.value.text.isEmpty()) {
                stringResource(id = R.string.error_required)
            } else if (viewModel.confirmPassword.value.text != viewModel.password.value.text) {
                stringResource(id = R.string.error_pass_not_match)
            } else {
                ""
            },
            onValueChanged = {
                if (viewModel.confirmPasswordErrorState.value) {
                    viewModel.confirmPasswordErrorState.value = false
                }
                viewModel.confirmPassword.value = it
            },
            stringRes = R.string.regis_confirm_pass,
            textFieldValue = viewModel.confirmPassword.value,
            isEnableTrailingIcon = true,
            isHidingPassword = cPasswordVisibility.value,
            onTrailingIconClicked = {
                cPasswordVisibility.value = !cPasswordVisibility.value
            }
        )

        Spacer(Modifier.size(154.dp))
        HyperlinkText(
            fullText = stringResource(id = R.string.regis_term_auto_agree),
            hyperlinks = listOf("https://halongpay.com", "https://halongpay.com"),
            linkText = listOf("Term & Conditions", "Privacy Policy"),
            linkTextColor = PrimaryColorYellow
        )
        Spacer(Modifier.size(5.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .align(CenterHorizontally),
            onClick = {
                viewModel.onNextPressed()
            },
            content = {
                Text(
                    text = stringResource(id = R.string.NEXT),
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = PrimaryColorYellow),
            shape = RoundedCornerShape(20.dp),
            contentPadding = PaddingValues(horizontal = 40.dp)
        )
        Spacer(Modifier.size(60.dp))
    }
}

@Composable
fun RegistrationField(
    isError: Boolean,
    errorMess: String,
    @StringRes stringRes: Int,
    textFieldValue: TextFieldValue,
    onValueChanged: (TextFieldValue) -> Unit,
    onTrailingIconClicked: (() -> Unit)? = null,
    isEnableTrailingIcon: Boolean = false,
    isHidingPassword: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    OutlinedTextField(
        value = textFieldValue,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = Color.LightGray.copy(alpha = 0.25F),
            unfocusedBorderColor = Color.LightGray.copy(alpha = 0.25F),
            unfocusedLabelColor = Color.DarkGray.copy(alpha = 0.6F),
            focusedLabelColor = PrimaryColorYellow,
            focusedBorderColor = PrimaryColorYellow
        ),
        onValueChange = {
            onValueChanged(it)
        },
        keyboardOptions = keyboardOptions,
        modifier = Modifier.fillMaxWidth(),
        isError = isError,
        label = {
            Text(
                text = stringResource(id = stringRes),
                fontStyle = FontStyle.Italic
            )
        },
        trailingIcon = {
            if (isEnableTrailingIcon) {
                IconButton(onClick = {
                    if (onTrailingIconClicked != null) {
                        onTrailingIconClicked()
                    }
                }) {
                    Icon(
                        imageVector = if (isHidingPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = "visibility",
                        tint = PrimaryColorYellow
                    )
                }
            }
        },
        visualTransformation = if (isHidingPassword && isEnableTrailingIcon) PasswordVisualTransformation() else VisualTransformation.None
    )
    if (isError) {
        Text(text = errorMess, color = Color.Red)
    }
    Spacer(modifier = Modifier.size(16.dp))
}
