package com.logde.carehome.screens.Informacion.favoritos


import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AssignmentInd
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.logde.carehome.model.Empleado
import com.logde.carehome.navigation.BottomNavigationBar
import com.logde.carehome.navigation.Screens
import com.logde.carehome.ui.theme.GreenSaveIt

@Composable
fun FavoritosScreen(navController: NavController) {

    val currentUser = FirebaseAuth.getInstance().currentUser
    val userId = currentUser?.uid

    if (userId == null) {
        Text("No estás logueado")
        return
    }

    val db = FirebaseFirestore.getInstance()
    val expenses = remember { mutableStateOf<List<Empleado>>(emptyList()) }

    // Cargar los gastos del usuario logueado
    LaunchedEffect(userId) {
        db.collection("favoritos")
            .whereEqualTo("uid", userId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val favoritosList = querySnapshot.documents.mapNotNull { document ->
                    // Crear un objeto Gasto incluyendo el documentId
                    val empleado = document.toObject(Empleado::class.java)
                    empleado?.apply {
                        // Añadir el documentId como campo adicional
                        id = document.id
                    }
                }
                expenses.value = favoritosList
            }
            .addOnFailureListener { e ->
                Log.e("favoritosList", "Error al cargar los gastos", e)
            }
    }

    // Interfaz de usuario
    Column(modifier = Modifier.fillMaxSize()) {
        // Contenido principal
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 20.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Mis Favoritos",
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                color = GreenSaveIt
            )
        }
        Box(
            modifier = Modifier
                .weight(1f) // La lista ocupa el espacio disponible
                .fillMaxSize()
        ) {
            if (expenses.value.isEmpty()) {
                // Mostrar mensaje si no hay gastos
                Text(
                    text = "No empleados registered.",
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.Black
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {
                    items(expenses.value) { empleado ->
                        FavoritosItem(empleado, navController, db, expenses)
                    }
                }
            }

        }

        // Barra de navegación inferior
        BottomNavigationBar(navController)
    }
}
@Composable
fun FavoritosItem(
    empleado: Empleado,
    navController: NavController,
    db: FirebaseFirestore,
    expenses: MutableState<List<Empleado>>
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(Color(0xFFF4F4F8)) // Color similar al de la imagen
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar con la inicial de la categoría
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xFFD9BBF9), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = empleado.nombre.firstOrNull()?.uppercase() ?: "",
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Información del empleado
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = empleado.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = empleado.titulo,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = empleado.descripcion,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.width(16.dp))


            Row {
                //Boton ver
                IconButton(
                    onClick = {
                        navController.navigate(Screens.GetFavoritosScreen.passFavoritosId(empleado.id))
                    }
                ) {
                    Icon(
                        Icons.Default.AssignmentInd,
                        contentDescription = "Edit",
                        modifier = Modifier.size(24.dp) // Tamaño personalizado del ícono
                    )
                }


                // Botón de eliminar
                IconButton(onClick = {
                    db.collection("favoritos").document(empleado.id)
                        .delete()
                        .addOnSuccessListener {
                            // Actualizar la lista de empleados después de eliminar
                            expenses.value = expenses.value.filterNot { it.id == empleado.id }
                            Toast.makeText(navController.context, "Empleado eliminado", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener { exception ->
                            Log.e("FavoritosItem", "Error al eliminar el empleado", exception)
                            Toast.makeText(navController.context, "Error al eliminar", Toast.LENGTH_SHORT).show()
                        }
                }) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.Red)
                }


            }
        }
    }
}
