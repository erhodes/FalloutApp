package com.erhodes.falloutapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.erhodes.falloutapp.ui.theme.Dimens
import com.erhodes.falloutapp.ui.theme.FalloutAppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun LoginScreen(loggedIn: Boolean, onLogin: (String, String) -> Unit, onSync: () -> Unit, modifier: Modifier = Modifier) {
    var name by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("10.0.0.214") }

    Column(
        modifier = modifier.fillMaxWidth()
            .padding(horizontal = Dimens.paddingSmall)
    ) {
        Text(
            text = "Login",
            style = MaterialTheme.typography.titleMedium
        )
        if (loggedIn) {
            Spacer(modifier = Modifier.height(8.dp))
            Text("Logged In successfully!")
        }

        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Login name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("Server Address") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { onLogin(name, address) }) {
            Text("Login")
        }
        Button(
            onClick = { onSync() }
        ) {
            Text("Sync Characters")
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    FalloutAppTheme {
        LoginScreen(true, onLogin = {a, b ->}, onSync = {})
    }
}