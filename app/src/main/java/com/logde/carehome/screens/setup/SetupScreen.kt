package com.example.saveit.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.logde.carehome.navigation.BottomNavigationBar
import com.google.firebase.auth.FirebaseAuth
import com.logde.carehome.R

@Composable
fun SetupScreen(navController : NavController) {
    val GreenSaveIt = Color(0xFFFF6F61) //
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Contenido principal de la pantalla
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
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
                Spacer(modifier = Modifier.height(16.dp))

                LogoutButton(navController, buttonColor = GreenSaveIt)
            }
        }

        // Barra de navegación en la parte inferior
        BottomNavigationBar(navController = navController)
    }
}


@Composable
fun LogoutButton(navController: NavController,buttonColor: Color) {
    Button(onClick = {
        // Cerrar sesión
        FirebaseAuth.getInstance().signOut()
        // Navegar a la pantalla de inicio de sesión
        navController.navigate("LoginScreen") {
            // Eliminar todas las pantallas anteriores para evitar volver atrás
            popUpTo(navController.graph.startDestinationId) {
                inclusive = true
            }
        }
    }, colors = ButtonDefaults.buttonColors(containerColor = buttonColor) // Aplica el color personalizado
    ) {
        Text("Log Out", color = Color.White)
    }
}