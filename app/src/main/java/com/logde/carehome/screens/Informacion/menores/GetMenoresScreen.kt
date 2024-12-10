package com.logde.carehome.screens.Informacion.menores


import android.app.DatePickerDialog
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.logde.carehome.R
import com.logde.carehome.model.Empleado
import com.logde.carehome.navigation.BottomNavigationBar
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.Calendar

@Composable
fun GetMenoresScreen(navController: NavController, empleadoId: String?) {
    val db = FirebaseFirestore.getInstance()
    val userId = FirebaseAuth.getInstance().currentUser?.uid

    // Verificar si el usuario está logueado
    if (userId == null) {
        Text("No estás logueado")
        return
    }

    var nombre by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var foto by remember { mutableStateOf("") }

    // Cargar los datos del empleado
    LaunchedEffect(empleadoId) {
        if (!empleadoId.isNullOrEmpty()) {
            db.collection("menores").document(empleadoId).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val empleado = document.toObject(Empleado::class.java)
                        if (empleado != null) {
                            nombre = empleado.nombre
                            category = empleado.categoria
                            telefono = empleado.telefono
                            titulo = empleado.titulo
                            descripcion = empleado.descripcion
                            foto = empleado.foto // URL de la imagen
                        }
                    }
                }
        }
    }

    // Componente principal
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Contenido principal
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Información Empleado",
                style = MaterialTheme.typography.titleLarge,
                color = Color.Red,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            // Foto del empleado
            if (foto.isNotEmpty()) {
                AsyncImage(
                    model = foto,
                    contentDescription = "Foto del empleado",
                    modifier = Modifier
                        .size(150.dp)
                        // .clip(CircleShape)
                        .border(2.dp, MaterialTheme.colorScheme.primary),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Campo para el nombre
            OutlinedTextField(
                value = nombre,
                onValueChange = {},
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true
            )


            Spacer(modifier = Modifier.height(16.dp))

            // Categoría (solo lectura)
            OutlinedTextField(
                value = category,
                onValueChange = {},
                label = { Text("Categoría") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Teléfono (solo lectura)
            OutlinedTextField(
                value = telefono,
                onValueChange = {},
                label = { Text("Teléfono") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Título (solo lectura)
            OutlinedTextField(
                value = titulo,
                onValueChange = {},
                label = { Text("Título") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Descripción (solo lectura)
            OutlinedTextField(
                value = descripcion,
                onValueChange = {},
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true
            )
        }

        // Barra de navegación inferior
        BottomNavigationBar(navController = navController)
    }
}
