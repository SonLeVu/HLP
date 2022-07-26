package com.sonlevu.halongpay.ui.dialogs

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sonlevu.halongpay.R

/**
 * Standard confirmation alert with "yes" "no" buttons, title and text
 */
@Composable
fun ConfirmationDialog(
    title: String,
    text: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    @DrawableRes iconId: Int? = null
) = AlertDialog(
    onDismissRequest = onDismiss,
    confirmButton = {
        TextButton(onClick = onConfirm) {
            Text(
                text = stringResource(R.string.ok),
                style = MaterialTheme.typography.titleMedium
            )
        }
    },
    dismissButton = {
        TextButton(onClick = onDismiss) {
            Text(
                text = stringResource(R.string.cancel),
                style = MaterialTheme.typography.titleMedium
            )
        }
    },
    title = {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge
        )
    },
    text = { Text(text) },
    icon = iconId?.let {
        {
            Icon(
                modifier = Modifier.size(26.dp),
                painter = painterResource(it),
                contentDescription = null
            )
        }
    }
)