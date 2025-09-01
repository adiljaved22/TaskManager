package com.example.taskmanager

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.taskmanager.data.SqlQuries
import com.example.taskmanager.data.UserEntity

@Composable
fun ProfileScreen(onLogout: () -> Unit, viewModel: userViewModel = viewModel()) {
    /*   val users by viewModel.getUser().collectAsState(initial = null)*/
    val context = LocalContext.current
    val sessionManager = SessionManager(context)
    val db = SqlQuries(context)
    val user: UserEntity? = db.getSingleUser()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = rememberAsyncImagePainter(user?.imageUri),
            contentDescription = "Profile Image",
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))
        user?.let { Text(it.username, fontWeight = FontWeight.Bold, fontSize = 35.sp) }
        user?.let { Text(it.email) }
        user?.let { Text(it.dateOfBirth.toString()) }

        Spacer(modifier = Modifier.height(24.dp))


        Button(onClick = {
            sessionManager.logout()
            onLogout()
        }) {

            Text("Logout")

        }

    }
}


