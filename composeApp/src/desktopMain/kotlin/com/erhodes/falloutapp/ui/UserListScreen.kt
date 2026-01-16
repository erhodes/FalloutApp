package com.erhodes.falloutapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.erhodes.falloutapp.model.User
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun UserListScreen(users: List<User>) {
    Column {
        Text("User List")
        users.forEach {
            UserDetail(it)
        }
    }
}

@Composable
fun UserDetail(user: User) {
    Row {
        Text(
            text = user.name,
            style = MaterialTheme.typography.bodyMedium
        )
        Text("(${user.uuid})")
    }

}

@Preview
@Composable
fun UserListScreenPreview() {
    val users = listOf(
        User("1", "Alice"),
        User("2", "Bob")
    )
    UserListScreen(users = users)
}
