package com.sonlevu.halongpay.ui.screens.registration

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sonlevu.halongpay.MainActivityViewModel
import com.sonlevu.halongpay.R
import com.sonlevu.halongpay.ui.theme.HalongpayTheme
import com.sonlevu.halongpay.ui.theme.ThemeSetting

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val viewModel: SignUpViewModel = viewModel()

            val darkTheme = when (viewModel.theme) {//by viewModel.theme.collectAsState()
                ThemeSetting.Light -> false
                ThemeSetting.Dark -> true
                ThemeSetting.System -> isSystemInDarkTheme()
            }

            HalongpayTheme(darkTheme) {
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    scaffoldState = scaffoldState,
                    snackbarHost = { hostState ->
                        SnackbarHost(
                            hostState = hostState,
                            modifier = Modifier.navigationBarsPadding()
                        ) { sbData ->
                            Snackbar(
                                snackbarData = sbData,
                                backgroundColor = MaterialTheme.colorScheme.surface,
                                contentColor = contentColorFor(MaterialTheme.colorScheme.surface),
                                shape = MaterialTheme.shapes.small
                            )
                        }
                    },
                    content = {
                        RegistrationScreen(viewModel = viewModel)
                    }
                )
            }
        }
    }
}