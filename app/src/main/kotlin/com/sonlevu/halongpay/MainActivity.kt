package com.sonlevu.halongpay

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sonlevu.halongpay.ui.screens.login.MainLoginScreen
import com.sonlevu.halongpay.ui.screens.registration.SignUpActivity
import com.sonlevu.halongpay.ui.theme.HalongpayTheme
import com.sonlevu.halongpay.ui.theme.ThemeSetting

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val viewModel: MainActivityViewModel = viewModel()

            val darkTheme = when (viewModel.theme) {//by viewModel.theme.collectAsState()
                ThemeSetting.Light -> false
                ThemeSetting.Dark -> true
                ThemeSetting.System -> isSystemInDarkTheme()
            }

            HalongpayTheme(darkTheme) {
                val scaffoldState = rememberScaffoldState()
                androidx.compose.material.Scaffold(
                    scaffoldState = scaffoldState,
                    snackbarHost = { hostState ->
                        androidx.compose.material.SnackbarHost(
                            hostState = hostState,
                            modifier = Modifier.navigationBarsPadding()
                        ) { sbData ->
                            androidx.compose.material.Snackbar(
                                snackbarData = sbData,
                                backgroundColor = MaterialTheme.colorScheme.surface,
                                contentColor = contentColorFor(MaterialTheme.colorScheme.surface),
                                shape = MaterialTheme.shapes.small
                            )
                        }
                    },
                    content = {
                        MainLoginScreen(
                            scaffoldState = scaffoldState,
                            signUp = {
                                val intent = Intent(this, SignUpActivity::class.java).apply {
                                    // putExtra(EXTRA_MESSAGE, message)
                                }
                                startActivity(intent)
                            },
                            paddingValues = it,
                        )
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HalongpayTheme {
        MainLoginScreen(rememberScaffoldState(), {}, PaddingValues(Dp(10F)))
    }
}