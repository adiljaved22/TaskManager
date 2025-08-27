package com.example.taskmanager

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
fun ProfileScreen(onLogout:()-> Unit, viewModel: userViewModel = viewModel()) {/*
    val user by viewModel.getUser().collectAsState(initial = null)*/

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

      /*  Image(
 *//*           painter = rememberAsyncImagePainter(user?.imageUri)*//*,
            contentDescription = "Profile Image",
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )*/

        Spacer(modifier = Modifier.height(16.dp))

        /*Text(text = user?.username ?: "Unknown", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Text(text = user?.email ?: "No Email")
        Text(text = user?.dateOfBirth?: "DOB not set")*/

        Spacer(modifier = Modifier.height(24.dp))


        Button(onClick = {

     onLogout()
 }) {
     Text("Logout")
 }

    }
}











