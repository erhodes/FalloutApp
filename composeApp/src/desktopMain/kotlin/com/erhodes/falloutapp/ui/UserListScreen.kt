package com.erhodes.falloutapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.erhodes.falloutapp.model.User
import com.erhodes.falloutapp.model.Character
import com.erhodes.falloutapp.presentation.UserCharacterGroup
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun UserListScreen(users: List<User>, groups: List<UserCharacterGroup>) {
    Column {
        Text("User List")
        users.forEach {
            UserDetail(it)
        }
        Spacer(modifier = Modifier.height(8.dp))

        groups.forEach {
            Text("User: ${it.userName}")
            it.characters.forEach { char ->
                Text("Character: ${char.name}")
            }
        }

//        Text("Characters")
//        characters.forEach {
//            Text("${it.name}, owned by ${it.ownerId}")
//        }
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
    val characters = listOf(
        Character(name = "Alice", ownerId = "1")
    )
    UserListScreen(users = users, arrayListOf(UserCharacterGroup("Alice", characters)))
}
