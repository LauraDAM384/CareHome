package com.logde.carehome.navigation
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.saveit.screens.home.ExpensesScreen
import com.example.saveit.screens.home.SetupScreen
import com.logde.carehome.screens.Informacion.favoritos.AddFavoritosScreen
import com.logde.carehome.screens.Informacion.favoritos.FavoritosScreen
import com.logde.carehome.screens.Informacion.favoritos.GetFavoritosScreen
import com.logde.carehome.screens.Informacion.limpieza.AddLimpiezaScreen
import com.logde.carehome.screens.Informacion.limpieza.GetLimpiezaScreen
import com.logde.carehome.screens.Informacion.limpieza.limpiezaScreen
import com.logde.carehome.screens.Informacion.mayores.AddMayoresScreen
import com.logde.carehome.screens.Informacion.mayores.GetMayoresScreen
import com.logde.carehome.screens.Informacion.mayores.MayoresScreen
import com.logde.carehome.screens.Informacion.menores.AddMenoresScreen
import com.logde.carehome.screens.Informacion.menores.GetMenoresScreen
import com.logde.carehome.screens.Informacion.menores.MenoresScreen

import com.logde.carehome.screens.splash.SplashScreen
import com.logde.carehome.screens.login.LoginScreen
import com.logde.carehome.screens.home.HomeScreen
import com.logde.carehome.screens.altaEmpleado.AddEmpleadoScreen



@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screens.SplashScreen.route) {
        composable(route = Screens.SplashScreen.route) {
            SplashScreen(navController)
        }
        composable(route = Screens.LoginScreen.route) {
            LoginScreen(navController)
        }
        composable(route = Screens.HomeScreen.route) {
            HomeScreen(navController)
        }
        composable(route = Screens.ExpensesScreen.route) {
            ExpensesScreen(navController)
        }

        composable(route = Screens.AddEmpleadoScreen.route) { backStackEntry ->
            val empleadoId = backStackEntry.arguments?.getString("empleadoId")
            AddEmpleadoScreen(navController, empleadoId)
        }
        composable(route = Screens.SetupScreen.route) {
            SetupScreen(navController)
        }
        composable(route = Screens.limpiezaScreen.route) {
            limpiezaScreen(navController)
        }
        composable(route = Screens.MayoresScreen.route) {
            MayoresScreen(navController)
        }
        composable(route = Screens.MenoresScreen.route) {
            MenoresScreen(navController)
        }

        composable(route = Screens.AddLimpiezaScreen.route) { backStackEntry ->
            val empleadoId = backStackEntry.arguments?.getString("empleadoId")
            AddLimpiezaScreen(navController, empleadoId)
        }

        composable(route = Screens.AddMenoresScreen.route) { backStackEntry ->
            val empleadoId = backStackEntry.arguments?.getString("empleadoId")
            AddMenoresScreen(navController, empleadoId)
        }

        composable(route = Screens.AddMayoresScreen.route) { backStackEntry ->
            val empleadoId = backStackEntry.arguments?.getString("empleadoId")
            AddMayoresScreen(navController, empleadoId)
        }
        composable(route = Screens.AddFavoritosScreen.route) { backStackEntry ->
            val empleadoId = backStackEntry.arguments?.getString("empleadoId")
            AddFavoritosScreen(navController, empleadoId)
        }
        composable(route = Screens.FavoritosScreen.route) {
            FavoritosScreen(navController)
        }
        composable(route = Screens.GetLimpiezaScreen.route) { backStackEntry ->
            val empleadoId = backStackEntry.arguments?.getString("empleadoId")
            GetLimpiezaScreen(navController, empleadoId)
        }
        composable(route = Screens.GetMenoresScreen.route) { backStackEntry ->
            val empleadoId = backStackEntry.arguments?.getString("empleadoId")
            GetMenoresScreen(navController, empleadoId)
        }
        composable(route = Screens.GetMayoresScreen.route) { backStackEntry ->
            val empleadoId = backStackEntry.arguments?.getString("empleadoId")
            GetMayoresScreen(navController, empleadoId)
        }
        composable(route = Screens.GetFavoritosScreen.route) { backStackEntry ->
            val empleadoId = backStackEntry.arguments?.getString("empleadoId")
            GetFavoritosScreen(navController, empleadoId)
        }


    }
}
