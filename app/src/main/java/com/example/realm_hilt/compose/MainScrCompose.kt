package com.example.realm_hilt.compose

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.realm_hilt.model.User
import com.example.realm_hilt.viewmodel.UserViewModel

@Composable
fun MainScrCompose(
    modifier: Modifier = Modifier,
    viewModel: UserViewModel
) {
    val openUpdateDialog = remember { mutableStateOf(false) }
    val openDeleteDialog = remember { mutableStateOf(false) }

    val context = LocalContext.current

    val userList = viewModel.users.observeAsState()
    val userId = remember { mutableStateOf("") }
    val userName = remember { mutableStateOf("") }
    val userPhone = remember { mutableStateOf("") }

    LazyColumn{
        items(userList.value!!) { item ->
            UserRowItemCompose(
                user = item,
                onClickUpdate = {
                    openUpdateDialog.value = true
                    userId.value = item.id
                    userName.value = item.name
                    userPhone.value = item.phone_number
                },
                onClickDelete = {
                    openDeleteDialog.value = true
                    userId.value = item.id
                }
            )
        }
    }

    if (openUpdateDialog.value) {
        AlertDialog(
            onDismissRequest = { openUpdateDialog.value = false },
            title = { Text(text = "Update User", ) },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 5.dp)
                ) {
                    TextField(
                        modifier = Modifier
                            .padding(vertical = 2.dp, horizontal = 3.dp)
                            .fillMaxWidth(),
                        value = userName.value,
                        onValueChange = {
                            userName.value = it
                        },
                        label = {
                            Text(text = "User Name")
                        },
                        placeholder = {
                            Text(text = "User Name")
                        }
                    )
                    TextField(
                        modifier = Modifier
                            .padding(vertical = 2.dp, horizontal = 3.dp)
                            .fillMaxWidth(),
                        value = userPhone.value,
                        onValueChange = {
                            userPhone.value = it
                        },
                        label = {
                            Text(text = "User Phone number")
                        },
                        placeholder = {
                            Text(text = "User Phone number")
                        }
                    )

                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openUpdateDialog.value = false
                        val result = viewModel.updateUser(User(id = userId.value, name = userName.value, phone_number = userPhone.value))
                        Toast.makeText(context,"Update Success $result", Toast.LENGTH_LONG).show()
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openUpdateDialog.value = false
                        Toast.makeText(context,"Precess Dismiss", Toast.LENGTH_LONG).show()
                    }
                ) {
                    Text("Dismiss")
                }
            }
        )
    }

    if (openDeleteDialog.value) {
        AlertDialog(
            onDismissRequest = { openDeleteDialog.value = false },
            title = { Text(text = "Delete User", ) },
            text = {
                Text(text = "Are you sure ...")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDeleteDialog.value = false
                        viewModel.deleteUser(userId.value)
                        Toast.makeText(context,"Delete Success", Toast.LENGTH_LONG).show()
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDeleteDialog.value = false
                        Toast.makeText(context,"Delete cancel", Toast.LENGTH_LONG).show()
                    }
                ) {
                    Text("Dismiss")
                }
            }
        )
    }

}