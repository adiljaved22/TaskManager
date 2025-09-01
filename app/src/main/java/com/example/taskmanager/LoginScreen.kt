package com.example.taskmanager

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.Unspecified
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.taskmanager.data.SqlQuries


@Composable
fun LoginScreen(
    NavigateToRegister: () -> Unit,
    NavigateTOLogin: () -> Unit,
    NavigateToHome: () -> Unit,

    viewModel: userViewModel


) {
    val context = LocalContext.current

    val sessionManager = SessionManager(context)
    val db = SqlQuries(context)


    var isLoggedIn by remember { mutableStateOf(sessionManager.isLoggedIn()) }

    Log.e("loginscreen start", "${sessionManager.isLoggedIn()}")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Login", fontWeight = FontWeight.Bold)

        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var emailError by rememberSaveable { mutableStateOf("") }
        var passwordError by rememberSaveable { mutableStateOf("") }
        var passwordVisible by remember { mutableStateOf(false) }

        if (sessionManager.isLoggedIn()) {
            Log.e("loginscreen", "${sessionManager.isLoggedIn()}")
            print("loginScreen=false")
            NavigateToHome()
        } else {
            Log.e("loginscreen else", "${sessionManager.isLoggedIn()}")

            print("loginScreen=true")
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                singleLine = true,
                label = {
                    Text(
                        text = emailError.ifEmpty { "Email" },
                        color = if (emailError.isNotEmpty()) Red else Unspecified
                    )
                },
                leadingIcon = { Icon(Icons.Rounded.Email, contentDescription = null) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                singleLine = true,
                label = {
                    Text(
                        text = passwordError.ifEmpty { "Password" },
                        color = if (passwordError.isNotEmpty()) Red else Unspecified
                    )
                },
                leadingIcon = { Icon(Icons.Rounded.Lock, contentDescription = null) },
                visualTransformation = if (passwordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation('*')
                },
                trailingIcon = {
                    val visibilityIcon =
                        if (passwordVisible) Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = visibilityIcon, contentDescription = null)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.height(12.dp))


        Button(
            onClick = {

                emailError = when {
                    email.isBlank() -> "Email is required"
                    !isValidEmail(email) -> "Invalid Email"
                    else -> ""
                }
                passwordError = when {
                    password.isBlank() -> "Password is required"
                    password.length < 6 -> "Password must be at least 6 characters"
                    else -> ""
                }


                if (emailError.isEmpty() && passwordError.isEmpty()) {
                    //This is a viewModel functionality for login
//                    viewModel.login(email, password) { success, message, user ->
//                        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
////                            val sessionManager = SessionManager(context)
//
//                        if (success && user != null) {
//                            Toast.makeText(context, "Login Success", Toast.LENGTH_LONG).show()
//                            sessionManager.saveLogin()
//                            NavigateTOLogin()
//                        } else {
//                            Toast.makeText(context, "Login Failed", Toast.LENGTH_LONG).show()
//                        }
//                    }

                    //This is sqlite login functionality
                    if (db.loginUser(email, password)) {
                        Toast.makeText(context, "Login Success", Toast.LENGTH_LONG).show()
                        sessionManager.saveLogin()
                        NavigateTOLogin()
                    } else {
                        Toast.makeText(context, "Login Failed", Toast.LENGTH_LONG).show()
                    }

                } else {
                    Toast.makeText(context, "Login Unsuccessful", Toast.LENGTH_LONG).show()
                }
            }

        ) {
            Text("Login")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row {
            Text("Not a Member? ")
            Text(
                "Sign Up",
                modifier = Modifier.clickable {
                    NavigateToRegister()
                },
                color = MaterialTheme.colorScheme.primary
            )
        }

    }
}

fun isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}
