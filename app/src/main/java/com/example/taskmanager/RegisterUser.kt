package com.example.taskmanager

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.Unspecified
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.taskmanager.data.UserEntity
import java.nio.file.WatchEvent

import java.util.Calendar
import kotlin.math.sin
import kotlin.text.ifEmpty


@Composable
fun RegisterUser(onBack: () -> Unit, viewModel: userViewModel) {
    var context = LocalContext.current
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var DOB by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmpassword by remember { mutableStateOf("") }
    var emailError by rememberSaveable { mutableStateOf("") }
    var passwordError by rememberSaveable { mutableStateOf("") }

    var selectedImage by remember { mutableStateOf<Uri?>(null) }
var passwordVisible by remember { mutableStateOf(false) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri -> selectedImage = uri
    if(uri!=null){
        context.contentResolver.takePersistableUriPermission(
            uri,
            Intent.FLAG_GRANT_READ_URI_PERMISSION
        )
    }


    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(Color.Gray)
                .clickable {
                    launcher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            if (selectedImage != null) {
                Image(
                    painter = rememberAsyncImagePainter(selectedImage),
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Text("Add Photo", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))


        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            singleLine = true)
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(value = email, onValueChange = { email = it },
            label = {
                Text(
                    text = emailError.ifEmpty { "Email" },
                    color = if (emailError.isNotEmpty()) Red else Unspecified
                )
                    }, singleLine = true)
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = DOB,
            onValueChange = { DOB = it },
            label = { Text("Date of Birth") },
            enabled = false,
            modifier = Modifier
                .clickable {
                    val calendar = Calendar.getInstance()
                    val year = calendar.get(Calendar.YEAR)
                    val month = calendar.get(Calendar.MONTH)
                    val date = calendar.get(Calendar.DATE)
                    android.app.DatePickerDialog(
                        context,
                        { _, selectedYear, selectedMonth, selectedDay ->
                            DOB = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                        },
                        year,
                        month,
                        date
                    ).show()
                }
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            singleLine = true,
            label = { Text(
                text = passwordError.ifEmpty { "Password" },
                color = if (passwordError.isNotEmpty()) Red else Unspecified
            ) },visualTransformation =
                if (passwordVisible) {
                    VisualTransformation.None

                } else {
                    PasswordVisualTransformation('*')
                },
            trailingIcon = {
                val visibilityIcon =
                    if (passwordVisible) {
                        Icons.Filled.Visibility
                    } else {
                        Icons.Filled.VisibilityOff
                    }
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = visibilityIcon, contentDescription = null)
                }
            })
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = confirmpassword,
            onValueChange = { confirmpassword = it },
            singleLine = true,
            label = { Text("Confirm Password") } ,
                    visualTransformation =
            if (passwordVisible) {
                VisualTransformation.None

            } else {
                PasswordVisualTransformation('*')
            },
            trailingIcon = {
                val visibilityIcon =
                    if (passwordVisible) {
                        Icons.Filled.Visibility
                    } else {
                        Icons.Filled.VisibilityOff
                    }
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = visibilityIcon, contentDescription = null)
                }
            })
        Spacer(modifier = Modifier.height(16.dp))

        Button(modifier = Modifier.fillMaxWidth().padding(start = 50.dp, end = 50.dp),
            onClick =
                {
                    emailError = when {
                        email.isBlank() -> "Email is required"
                        !isValidEmail(email) -> "Invalid Email"
                        else -> ""
                    }
                    passwordError = when {
                        password.isBlank() -> "password is required"
                        password.length < 6 -> "Password must be at least 6 characters"
                        else -> ""
                    }
                    if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmpassword.isEmpty() || selectedImage == null) {
                        Toast.makeText(context, "Please fill all fields", Toast.LENGTH_LONG).show()

                    } else if (password != confirmpassword) {
                        Toast.makeText(context, "Passwords do not match", Toast.LENGTH_LONG).show()
                    }


                    else {
                        val imageUri=selectedImage?.toString()?:""
                        val user = UserEntity(
                            username = username,
                            email = email,
                            password = password,
                            imageuri = imageUri,
                            dateofbirth = DOB
                        )

                        viewModel.register(user) { success, message ->
                            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                            if (success) {
                                UserSession.currentUser= user
                                onBack()
                            }
                        }
                    }

                },
        ) {
            Text("Register")
        }
    }
}