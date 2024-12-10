package com.logde.carehome.screens.home


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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.logde.carehome.R
import com.logde.carehome.navigation.BottomNavigationBar
import com.logde.carehome.navigation.Screens

@Composable
fun HomeScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(Color(0xFFD1F0E4), Color(0xFFB3D9FF))
                )
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally, // Asegura que los elementos estén centrados horizontalmente
            verticalArrangement = Arrangement.SpaceBetween // Distribuye el contenido verticalmente
        ) {
            Spacer(modifier = Modifier.height(10.dp)) // Espacio superior

            // Contenido principal en el centro
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally, // Centra los elementos horizontalmente dentro de este Column
                verticalArrangement = Arrangement.spacedBy(16.dp) // Espacio entre los elementos
            ) {
                ButtonWithIcon(
                    text = "Asistente Limpieza",
                    onClick = { navController.navigate("limpiezaScreen") },
                    iconId = R.drawable.limpieza
                )
                Spacer(modifier = Modifier.height(10.dp))
                ButtonWithIcon(
                    text = "Cuidado Menores",
                    onClick = { navController.navigate("MenoresScreen") },
                    iconId = R.drawable.cuidadomenores
                )
                Spacer(modifier = Modifier.height(10.dp))
                ButtonWithIcon(
                    text = "Cuidado Mayores",
                    onClick = { navController.navigate("MayoresScreen") },
                    iconId = R.drawable.personasmayores
                )
            }

            // BottomNavigationBar en la parte inferior
            BottomNavigationBar(navController = navController)
        }
    }
}





@Composable
fun ButtonWithIcon(text: String, onClick: () -> Unit, iconId: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Ícono
        Image(
            painter = painterResource(id = iconId),
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .padding(8.dp)
        )
        // Botón
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(Color(0xFFFF6F61)),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(48.dp)
        ) {
            Text(text = text, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}
