package com.sonlevu.halongpay.ui.screens.registration

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sonlevu.halongpay.R
import com.sonlevu.halongpay.ui.theme.PrimaryColorYellow
import com.sonlevu.halongpay.ui.theme.SecondaryColorNavy
import com.sonlevu.halongpay.ui.utils.HyperlinkText

@Composable
fun RegistrationScreen(viewModel: SignUpViewModel) {
    val context = LocalContext.current

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center,
    ) {
        Spacer(Modifier.size(10.dp))
        Text(text = stringResource(R.string.sign_up_instruction))
        Spacer(Modifier.size(40.dp))
        OutlinedTextField(
            value = viewModel.name.value,
            onValueChange = {
                if (viewModel.nameErrorState.value) {
                    viewModel.nameErrorState.value = false
                }
                viewModel.name.value = it
            },

            modifier = Modifier.fillMaxWidth(),
            isError = viewModel.nameErrorState.value,
            label = {
                Text(
                    text = stringResource(id = R.string.regis_fullname),
                    fontStyle = FontStyle.Italic
                )
            },
        )
        if (viewModel.nameErrorState.value) {
            Text(text = stringResource(id = R.string.error_required), color = Color.Red)
        }
        Spacer(Modifier.size(16.dp))

        OutlinedTextField(
            value = viewModel.email.value,
            onValueChange = {
                if (viewModel.emailErrorState.value) {
                    viewModel.emailErrorState.value = false
                }
                viewModel.email.value = it
            },

            modifier = Modifier.fillMaxWidth(),
            isError = viewModel.emailErrorState.value,
            label = {
                Text(text = "Email", fontStyle = FontStyle.Italic)
            },
        )
        if (viewModel.emailErrorState.value) {
            Text(text = stringResource(id = R.string.error_required), color = Color.Red)
        }
        Spacer(modifier = Modifier.size(16.dp))

        OutlinedTextField(
            value = viewModel.mobileNo.value,
            onValueChange = {
                viewModel.mobileNo.value = it
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                autoCorrect = false
            ),
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    text = stringResource(id = R.string.regis_mobile),
                    fontStyle = FontStyle.Italic
                )
            },
        )

        Spacer(Modifier.size(16.dp))
        val passwordVisibility = remember { mutableStateOf(true) }
        OutlinedTextField(
            value = viewModel.password.value,
            onValueChange = {
                if (viewModel.passwordErrorState.value) {
                    viewModel.passwordErrorState.value = false
                }
                viewModel.password.value = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = "Password", fontStyle = FontStyle.Italic)
            },
            isError = viewModel.passwordErrorState.value,
            trailingIcon = {
                IconButton(onClick = {
                    passwordVisibility.value = !passwordVisibility.value
                }) {
                    Icon(
                        imageVector = if (passwordVisibility.value) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = "visibility",
                        tint = SecondaryColorNavy
                    )
                }
            },
            visualTransformation = if (passwordVisibility.value) PasswordVisualTransformation() else VisualTransformation.None
        )
        if (viewModel.passwordErrorState.value) {
            Text(text = stringResource(id = R.string.error_required), color = Color.Red)
        }

        Spacer(Modifier.size(16.dp))
        val cPasswordVisibility = remember { mutableStateOf(true) }
        OutlinedTextField(
            value = viewModel.confirmPassword.value,
            onValueChange = {
                if (viewModel.confirmPasswordErrorState.value) {
                    viewModel.confirmPasswordErrorState.value = false
                }
                viewModel.confirmPassword.value = it
            },
            modifier = Modifier.fillMaxWidth(),
            isError = viewModel.confirmPasswordErrorState.value,
            label = {
                Text(text = "Confirm Password", fontStyle = FontStyle.Italic)
            },
            trailingIcon = {
                IconButton(onClick = {
                    cPasswordVisibility.value = !cPasswordVisibility.value
                }) {
                    Icon(
                        imageVector = if (cPasswordVisibility.value) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = "visibility",
                        tint = SecondaryColorNavy
                    )
                }
            },
            visualTransformation = if (cPasswordVisibility.value) PasswordVisualTransformation() else VisualTransformation.None
        )
        if (viewModel.confirmPasswordErrorState.value) {
            val msg = if (viewModel.confirmPassword.value.text.isEmpty()) {
                stringResource(id = R.string.error_required)
            } else if (viewModel.confirmPassword.value.text != viewModel.password.value.text) {
                "Password not matching"
            } else {
                ""
            }
            Text(text = msg, color = Color.Red)
        }
        Spacer(Modifier.size(190.dp))
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
        Spacer(Modifier.size(16.dp))
    }
}
