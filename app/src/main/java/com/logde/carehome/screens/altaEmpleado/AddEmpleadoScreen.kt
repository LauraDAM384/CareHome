package com.logde.carehome.screens.altaEmpleado

import android.app.DatePickerDialog
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.logde.carehome.model.Empleado
import com.logde.carehome.navigation.BottomNavigationBar
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.Calendar

@Composable
fun AddEmpleadoScreen(navController: NavController, empleadoId: String?) {
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
    var isUpdateMode by remember { mutableStateOf(false) }

    val categories = listOf("limpieza", "menores", "mayores") // Categorías predefinidas

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // Si estamos editando, cargar los datos del gasto
    LaunchedEffect(empleadoId) {
        if (!empleadoId.isNullOrEmpty()) {
            db.collection("empleados").document(empleadoId).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val empleado = document.toObject(Empleado::class.java)
                        if (empleado != null) {
                            nombre = empleado.nombre
                            category = empleado.categoria
                            telefono = empleado.telefono
                            titulo = empleado.titulo
                            descripcion = empleado.descripcion
                            isUpdateMode = true
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
                text = if (isUpdateMode) "Edit empleado" else "Add empleado",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Campo para el concepto
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("nombre") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Categoría (desplegable) y cantidad
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Lista desplegable para categoría
                var expanded by remember { mutableStateOf(false) }

                Box(modifier = Modifier.weight(1f)) {
                    OutlinedTextField(
                        value = category,
                        onValueChange = { /* Nada, solo seleccionable desde el menú */ },
                        label = { Text("categoria") },
                        readOnly = true,
                        modifier = Modifier.fillMaxWidth(),
                        trailingIcon = {
                            IconButton(onClick = { expanded = true }) {
                                Icon(
                                    Icons.Default.ArrowDropDown,
                                    contentDescription = "Abrir categorías"
                                )
                            }
                        }
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        categories.forEach { cat ->
                            DropdownMenuItem(
                                text = { Text(cat) },
                                onClick = {
                                    category = cat
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }

                // Cantidad
                OutlinedTextField(
                    value = telefono,
                    onValueChange = { telefono = it },
                    label = { Text("telefono") },
                    modifier = Modifier.fillMaxWidth()
                )


                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = titulo,
                    onValueChange = { titulo = it },
                    label = { Text("titulo") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("descripcion") },
                    modifier = Modifier.fillMaxWidth()
                )





            Spacer(modifier = Modifier.height(16.dp))

            // Botón para añadir o actualizar
            Button(
                onClick = {
                    if (nombre.isNotEmpty() && category.isNotEmpty() && telefono.isNotEmpty() && titulo.isNotEmpty() && descripcion.isNotEmpty()) {
                        val expense = hashMapOf(
                            "nombre" to nombre,
                            "categoria" to category,
                            "telefono" to telefono,
                            "titulo" to titulo,
                            "descripcion" to descripcion,

                            "uid" to userId
                        )

                        coroutineScope.launch {
                            try {
                                if (isUpdateMode) {
                                    // Actualizar el gasto en Firebase
                                    db.collection("empleados").document(empleadoId!!).set(expense)
                                        .await()
                                } else {
                                    // Guardar el gasto en Firebase
                                    db.collection("empleados")
                                        .add(expense)
                                        .await()  // Usar .await() para hacer la llamada asíncrona
                                }

                                // Si la inserción o actualización es exitosa, regresar a la pantalla anterior
                                navController.popBackStack()
                            } catch (e: Exception) {
                                // Si ocurre un error, mostrar un mensaje
                                Log.e("AddExpense", "Error al agregar o actualizar el gasto", e)
                                Toast.makeText(
                                    context,
                                    "Error al agregar o actualizar el gasto",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
            ) {
                Text(
                    text = if (isUpdateMode) "Update" else "Add",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }


        // Barra de navegación inferior
        BottomNavigationBar(navController = navController)
    }
}


