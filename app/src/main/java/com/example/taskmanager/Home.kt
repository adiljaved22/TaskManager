package com.example.taskmanager

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.taskmanager.data.SqlQuries
import com.example.taskmanager.data.TaskEntity
import com.example.taskmanager.data.UserEntity


@Composable
fun Home(
    NavigateToTask: () -> Unit,
    NavigateToProfile: () -> Unit,
    NavigateToEdit: (String) -> Unit,
    onBack: () -> Unit,
    viewModel: userViewModel = viewModel()
) {
    var dialogBox by remember { mutableStateOf(false) }
//    val list by viewModel.getall.collectAsState(initial = emptyList())
    val context = LocalContext.current
    val db = SqlQuries(context)
    var selectedTaskId by remember { mutableStateOf<Int?>(null) }
    val user: UserEntity? = db.getSingleUser()
    val tasks = remember { mutableStateListOf<TaskEntity>() }
    LaunchedEffect(Unit) {
        tasks.clear()
        tasks.addAll(db.getAllTask())
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (user?.imageUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(user?.imageUri),
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                        .clickable { NavigateToProfile() },
                )
                Spacer(modifier = Modifier.width(8.dp))
                user?.let {
                    Text(it.username, fontWeight = FontWeight.Medium)
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text("Tasks", fontWeight = FontWeight.Bold, fontSize = 22.sp)
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 10.dp)
        ) {
            items(tasks) { task ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(6.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {

                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp))
                    {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {

                            Text(task.title, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(task.description, fontSize = 15.sp)
                        }
                        IconButton(onClick = { NavigateToEdit("edit/${task.id}") }) {

                            Icon(imageVector = Icons.Default.Edit, contentDescription = null)
                        }
                        if (dialogBox) {
                            AlertDialog(
                                onDismissRequest = { dialogBox = false },
                                title = { Text("Are you want To delete this item? ") },

                                confirmButton = {
                                    Button(onClick = {
                                        db.deleteTask(selectedTaskId!!)
                                        tasks.clear()

                                        tasks.addAll(db.getAllTask())
                                        dialogBox = false
                                        selectedTaskId = null
                                    }) {
                                        Text("Confirm")
                                    }
                                },

                                dismissButton = {
                                    Button(onClick = { dialogBox = false }) {
                                        Text("Dismiss")
                                    }
                                }
                            )

                        }
                        IconButton(onClick = {
                            selectedTaskId = task.id
                            dialogBox = true
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = null)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
    }
    Box(
        contentAlignment = Alignment.BottomEnd
    ) {

        FloatingActionButton(
            onClick = { NavigateToTask() },
            containerColor = Color.Black,
            contentColor = Color.White,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Add Task")
        }

    }


}


/*LaunchedEffect(user?.email) {
        user?.let {
            viewModel.loadTasks(it.email)
        }
    }*/