package com.example.taskmanager

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.contracts.contract

@Composable
fun AddTask(onBack: () -> Unit) {
    var context = LocalContext.current
    var Title by remember { mutableStateOf("") }
    var Description by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Text(
            "Add Task ",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        OutlinedTextField(
            value = Title,
            onValueChange = {
                Title = it
            },
            label =
                {
            Text("Title")
        })
        Spacer(modifier = Modifier.padding(8.dp))
        OutlinedTextField(
            value = Description,
            onValueChange = { Description = it },
            label = { Text("Description") })
        Button(onClick = {
            if (Title.isNotBlank() && Description.isNotBlank()) {
                Responsible.savedlist.add(TaskItem(Title,Description))
                Title=""
                Description=""
                onBack()
            } else {
                Toast.makeText(
                    context, "Fill All Blanks",
                    Toast.LENGTH_LONG)
                    .show()
            }

        }) {
            Text("Add", fontSize = 21.sp)
        }
    }
}