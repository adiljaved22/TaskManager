package com.example.taskmanager

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter


@Composable
fun Home(
    NavigateToTask: () -> Unit,
    NavigateToProfile: () -> Unit,
    viewModel: userViewModel = viewModel()
) {
 /*   val user by viewModel.getUser().collectAsState(initial = null)*/
    val list by viewModel.getall.collectAsState(initial = emptyList())
    val users by viewModel.getUser().collectAsState(initial = null)

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {


        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (users?.imageUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(users?.imageUri),
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                        .clickable { NavigateToProfile() },
                    contentScale = ContentScale.Crop
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text("Tasks", fontWeight = FontWeight.Bold, fontSize = 22.sp)
        }

        Spacer(modifier = Modifier.height(12.dp))


        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(list) { task ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(6.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(task.title, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(task.description, fontSize = 15.sp, color = Color.DarkGray)
                    }
                }
            }
        }
    }


    Box(
        modifier = Modifier.fillMaxSize(),
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