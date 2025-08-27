package com.example.taskmanager.TaskEntity


import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.taskmanager.data.TaskEntity
import com.example.taskmanager.userViewModel

@Composable
fun AddTask(viewModel: userViewModel = viewModel(), onBack: () -> Unit) {
val user by viewModel.getUser().collectAsState(initial = null)
    val context = LocalContext.current
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Add Task",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") }
        )

        Spacer(modifier = Modifier.padding(8.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") }
        )

        Button(onClick = {
            if (title.isNotBlank() && description.isNotBlank()) {

                val newtask = TaskEntity(title = title, description = description)

                viewModel.addTask(newtask)
                title = ""
                description = ""


                onBack()
            } else {
                Toast.makeText(context, "Fill All Blanks", Toast.LENGTH_LONG).show()
            }
        }) {
            Text("Add", fontSize = 21.sp)
        }
    }
}