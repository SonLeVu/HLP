package com.sonlevu.halongpay.utils

import androidx.annotation.StringRes
import com.sonlevu.halongpay.R

inline fun <T> MutableResultFlow<T>.loadOrError(
    @StringRes messageId: Int = R.string.common_error_message,
    preserveValue: Boolean = true,
    showLoading: Boolean = true,
    load: () -> T?
) {
    if (showLoading) {
        value = LoadingResult(value.data.takeIf { preserveValue })
    }

    value = try {
        SuccessResult(load())
    } catch (e: Exception) {
        print(e)
        ErrorResult(messageId)
    }
}