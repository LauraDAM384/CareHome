package com.logde.carehome.navigation

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        Screens.HomeScreen,
        Screens.FavoritosScreen,
        Screens.SetupScreen
    )
    val currentRoute = navController.currentBackStackEntryAsState()?.value?.destination?.route

    NavigationBar {
        items.forEach { screen ->
            val isSelected = currentRoute == screen.route
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = when (screen) {
                            Screens.HomeScreen -> Icons.Default.Home
                            Screens.FavoritosScreen -> Icons.Default.Favorite
                            Screens.SetupScreen -> Icons.Default.Settings
                            else -> Icons.Default.Home
                        },
                        contentDescription = screen.route
                    )
                },
                label = null,
                selected = isSelected,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(Screens.HomeScreen.route) { inclusive = false }
                    }
                },
                alwaysShowLabel = false,
                modifier = if (isSelected) {
                    Modifier.height(60.dp) // Altura extra para agregar la raya
                } else Modifier
            )

            if (isSelected) {
                Surface(
                    color = Color.Blue, // Cambia al color deseado
                    shape = RoundedCornerShape(topStart = 2.dp, topEnd = 2.dp),
                    modifier = Modifier.height(2.dp)
                ) {}
            }
        }
    }
}
