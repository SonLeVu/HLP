package com.sonlevu.halongpay.ui.screens.registration

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sonlevu.halongpay.R
import com.sonlevu.halongpay.ui.theme.HalongpayTheme
import com.sonlevu.halongpay.ui.theme.ThemeSetting
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

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
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Spacer(Modifier.size(24.dp))
                    val state = rememberCollapsingToolbarScaffoldState()
                    CollapsingToolbarScaffold(
                        modifier = Modifier
                            .fillMaxSize(),
                        state = state,
                        scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
                        toolbar = {
                            val paddingBeginning = (48 - 30 * state.toolbarState.progress).dp
                            val paddingTop = (17 + 30 * state.toolbarState.progress).dp
                            Text(
                                text = stringResource(id = R.string.regis_title),
                                maxLines = 1,
                                modifier = Modifier
                                    .road(whenCollapsed = Alignment.Center, whenExpanded = Alignment.BottomStart)
                                    .padding(paddingBeginning, paddingTop, 1.dp, 1.dp),
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                            IconButton(onClick = {
                                finish()
                            }) {
                                Icon(
                                    modifier = Modifier
                                        .pin()
                                        .padding(16.dp),
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = "visibility",
                                    tint = Color.Black
                                )
                            }
                        }
                    ) {
                        RegistrationScreen(viewModel = viewModel)
                    }
                }
            }
        }
    }
}