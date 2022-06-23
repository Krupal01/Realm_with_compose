package com.example.realm_hilt.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.realm_hilt.model.User

@Composable
fun UserRowItemCompose(
    modifier: Modifier = Modifier,
    user: User = User("-", "-" ,"-"),
    onClickDelete : () -> Unit,
    onClickUpdate : () -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .padding(all = 5.dp)
    ) {
        Text(
            text = user.name,
            fontSize = 20.sp,
            fontWeight = FontWeight.Black
        )
        Text(
            text = user.phone_number,
            fontSize = 15.sp,
        )
        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                modifier = Modifier
                    .weight(1f)
                    .padding(all = 3.dp),
                onClick = onClickDelete
            ) {
                Text(text = "Delete")
            }
            Button(
                modifier = Modifier
                    .weight(1f)
                    .padding(all = 3.dp),
                onClick = onClickUpdate
            ) {
                Text(text = "Update")
            }
        }
    }
}