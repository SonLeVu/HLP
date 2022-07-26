package com.sonlevu.halongpay.ui.screens.registration

import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import com.sonlevu.halongpay.ui.theme.ThemeSetting

class SignUpViewModel: ViewModel() {
    val name = mutableStateOf(TextFieldValue())
    val email = mutableStateOf(TextFieldValue())
    val mobileNo = mutableStateOf(TextFieldValue())
    val password = mutableStateOf(TextFieldValue())
    val confirmPassword = mutableStateOf(TextFieldValue())

    val nameErrorState = mutableStateOf(false)
    val emailErrorState = mutableStateOf(false)
    val passwordErrorState = mutableStateOf(false)
    val confirmPasswordErrorState = mutableStateOf(false)

    val theme = ThemeSetting.Light

    init{

    }

    fun onNextPressed() {
        when {
            name.value.text.isEmpty() -> {
                nameErrorState.value = true
            }
            email.value.text.isEmpty() -> {
                emailErrorState.value = true
            }
            password.value.text.isEmpty() -> {
                passwordErrorState.value = true
            }
            confirmPassword.value.text.isEmpty() -> {
                confirmPasswordErrorState.value = true
            }
            confirmPassword.value.text != password.value.text -> {
                confirmPasswordErrorState.value = true
            }
            else -> {
//                Toast.makeText(
//                    context,
//                    "Registered successfully",
//                    Toast.LENGTH_SHORT
//                ).show()
            }
        }
    }
}