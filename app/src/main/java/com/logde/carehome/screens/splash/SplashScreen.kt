package com.logde.carehome.screens.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.logde.carehome.R
import com.logde.carehome.navigation.Screens
import com.logde.carehome.ui.theme.GreenSaveIt
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(navController: NavController) {
    var progress by remember { mutableStateOf(0f) }

    // Animación de escala
    val scale = remember { Animatable(0f) }

    // Animación de rotación
    val rotationAngle = remember { Animatable(0f) }

    LaunchedEffect(key1 = true) {

        // Primero animamos la escala (de 0 a 1)
        scale.animateTo(
            targetValue = 1f, // El tamaño final de la imagen (100%)
            animationSpec = tween(durationMillis = 2000, easing = FastOutSlowInEasing) // Duración y suavizado
        )

        // Después de la animación de escala, esperamos un pequeño momento antes de empezar la rotación
        delay(500) // Puedes ajustar este retraso si lo deseas

        // Luego animamos la rotación (de 0 a 360 grados)
        rotationAngle.animateTo(
            targetValue = 360f, // Rotación completa
            animationSpec = tween(durationMillis = 1000, easing = LinearEasing) // Duración y suavizado
        )

        // Después de la animación de rotación, navegamos a la pantalla de login
        delay(1000)

        navController.navigate(Screens.LoginScreen.route)  // Navegar a la pantalla de login

    }

    val color = MaterialTheme.colorScheme.primary

    Surface(
        modifier = Modifier
            .padding(15.dp)
            .size(250.dp)
            .scale(scale.value)  // Aplicar la animación de escala
           // .graphicsLayer {
             //   rotationZ = rotationAngle.value // Aplicar la rotación sobre el eje Z
           // }
                ,
        //shape = CircleShape,
        border = BorderStroke(width = 2.dp, color = color)
    ) {
        Column(
            modifier = Modifier.padding(1.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Mostrar la imagen que se escala y luego rota
            Image(
                painter = painterResource(id = R.drawable.logocarehome), // Reemplaza con tu imagen
                contentDescription = "Logo animado",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp) // Ajusta el padding según lo necesites
            )
        }
    }
}
