package com.logde.carehome.screens.login

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.text.TextStyle

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.logde.carehome.R

import com.logde.carehome.ui.theme.GreenSaveIt
import com.logde.carehome.ui.theme.ButtonColor

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginScreenViewModel = viewModel()
) {
    val auth = FirebaseAuth.getInstance()
    val context = LocalContext.current
    val showLoginForm = rememberSaveable { mutableStateOf(true) }
    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val passwordVisible = rememberSaveable { mutableStateOf(false) }

    // Google Authentication
    val token = "1035289018177-cg4e6ht6gjijp6eafjjluj2bs0lhrv6b.apps.googleusercontent.com"  // Replace with your actual token
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            auth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        navController.navigate("HomeScreen")
                    } else {
                        Log.d("Login", "Google sign-in failed")
                    }
                }
        } catch (ex: Exception) {
            Log.d("Login", "Google sign-in failed")
        }
    }

    // Layout
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.logocarehome), // Cambia a tu recurso de imagen
            contentDescription = "Logo",
            modifier = Modifier
                .size(200.dp) // Ajusta el tamaño de la imagen
                .padding(bottom = 16.dp), // Espaciado entre la imagen y el texto
            contentScale = ContentScale.Fit
        )

        Text("CARE HOME",Modifier.padding(top = 10.dp,bottom = 60.dp) , style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.Bold), color = GreenSaveIt)

        // Show Login or Sign Up Form
        if (showLoginForm.value) {
            UserForm(isCreateAccount = false, showLoginForm = showLoginForm) { email, password ->
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            navController.navigate("HomeScreen")
                        } else {
                            Toast.makeText(context, "Authentication failed", Toast.LENGTH_SHORT).show()
                            Log.d("Login", "Authentication failed")
                        }
                    }
            }
        } else {
            UserForm(isCreateAccount = true, showLoginForm = showLoginForm) { email, password ->
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            navController.navigate("HomeScreen")
                        } else {
                            Log.d("Login", "Account creation failed")
                        }
                    }
            }
        }



        Row(modifier = Modifier.fillMaxWidth().padding(top = 10.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
            Divider(modifier = Modifier.weight(1f), color = Color.DarkGray)  // Divisor izquierdo
            Text(
                text = "or",
                color = Color.DarkGray,
                modifier = Modifier.padding(horizontal = 8.dp)  // Espaciado entre el texto y los divisores
            )
            Divider(modifier = Modifier.weight(1f), color = Color.DarkGray)  // Divisor derecho
        }

        // Google Sign-In Button

        Button(
            onClick = {
                val googleSignInOptions = GoogleSignInOptions
                    .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(token)
                    .requestEmail()
                    .build()
                val googleSignInClient = GoogleSignIn.getClient(context, googleSignInOptions)
                launcher.launch(googleSignInClient.signInIntent)
            },
            modifier = Modifier.fillMaxWidth().padding(3.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = GreenSaveIt )
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_google),
                    contentDescription = "Google Logo",
                    modifier = Modifier.size(20.dp),
                    tint = Color.Unspecified
                )
                Text(
                    text = "Continue with Google",
                    modifier = Modifier.padding(start = 10.dp),
                    style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold),
                    color = Color.White
                )
            }
        }



        // Switch between Login and Sign-Up
        Spacer(Modifier.weight(1f))
        Row(
            modifier = Modifier.padding(bottom = 90.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val text1 = if (showLoginForm.value) "Don't have an account?" else "Have an account?"
            val text2 = if (showLoginForm.value) "Sign Up" else "Log In"
            Text(text = text1, color = Color.DarkGray, style = TextStyle(fontSize = 14.sp))
            Text(text = text2,
                modifier = Modifier
                    .clickable { showLoginForm.value = !showLoginForm.value }
                    .padding(start = 5.dp),
                color = GreenSaveIt,
                style = TextStyle(fontWeight = FontWeight.ExtraBold)

            )
        }
    }
}

@Composable
fun UserForm(isCreateAccount: Boolean, showLoginForm: MutableState<Boolean>, onDone: (String, String) -> Unit) {
    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val passwordVisible = rememberSaveable { mutableStateOf(false) }

    val isFormValid = email.value.isNotEmpty() && password.value.isNotEmpty()

    val keyboardController = LocalSoftwareKeyboardController.current


    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        EmailInput(emailState = email)
        PasswordInput(passwordState = password, passwordVisible = passwordVisible)
        SubmitButton(textId = if (showLoginForm.value) "Login" else "Create an account", inputValido = isFormValid) {
            onDone(email.value.trim(), password.value.trim())
            keyboardController?.hide()
        }
    }
}

@Composable
fun SubmitButton(textId: String, inputValido: Boolean, onClic: () -> Unit) {
    Button(
        onClick = onClic,
        modifier = Modifier.fillMaxWidth().padding(3.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors( containerColor =  GreenSaveIt),
        enabled = inputValido
    ) {
        Text(text = textId, modifier = Modifier.padding(5.dp), style = TextStyle(fontWeight = FontWeight.Bold))
    }
}

@Composable
fun EmailInput(emailState: MutableState<String>) {
    InputField(valuestate = emailState, placeholder = "Email", keyboardType = KeyboardType.Email)
}

@Composable
fun PasswordInput(passwordState: MutableState<String>, passwordVisible: MutableState<Boolean>) {
    val visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation()
    OutlinedTextField(
        value = passwordState.value,
        onValueChange = { passwordState.value = it },
        placeholder = { Text(text = "Password", color = Color.Black) },
        visualTransformation = visualTransformation,
        trailingIcon = {
            IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                Icon(
                    imageVector = if (passwordVisible.value) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                    contentDescription = "Toggle password visibility"
                )
            }
        },
        colors = TextFieldDefaults.colors(
            Color.Black,
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
        ),
        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
    )
}

@Composable
fun InputField(valuestate: MutableState<String>, placeholder: String, keyboardType: KeyboardType) {
    OutlinedTextField(
        value = valuestate.value,
        onValueChange = { valuestate.value = it },
        placeholder = { Text(text = placeholder, color = Color.Black) },
        singleLine = true,
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.colors(
            Color.Black,
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
        ),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
    )
}
