package com.sonlevu.halongpay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sonlevu.halongpay.ui.theme.HalongpayTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HalongpayTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainLoginScreen()
                }
            }
        }
    }
}

@Composable
fun MainLoginScreen() {
    ElevatedButton(onClick = {
    /* Do something! */
    }) {
        Text("Elevated Button")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HalongpayTheme {
        MainLoginScreen()
    }
}