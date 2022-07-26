package com.sonlevu.halongpay.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sonlevu.halongpay.utils.MutableResultFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    val loginResult = MutableResultFlow<Unit>()

    init {}

    fun login(username: String, password: String) = viewModelScope.launch {
        //encode password
        // Login sequence
    }

    fun createNewAccount() {

    }

}