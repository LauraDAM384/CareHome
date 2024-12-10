package com.logde.carehome.navigation

sealed class Screens(val route: String) {
    object SplashScreen : Screens("SplashScreen")
    object LoginScreen : Screens("LoginScreen")
    object HomeScreen : Screens("HomeScreen")
    object ExpensesScreen : Screens("ExpensesScreen")
    object AddEmpleadoScreen : Screens("AddEmpleadoScreen/{empleadoId}") {
        fun passEmpleadoId(empleadoId: String) = "AddEmpleadoScreen/$empleadoId"
    }


    object SetupScreen : Screens("SetupScreen")
    object limpiezaScreen: Screens("limpiezaScreen")
    object MayoresScreen: Screens("MayoresScreen")
    object MenoresScreen: Screens("MenoresScreen")
    object AddLimpiezaScreen : Screens("AddLimpiezaScreen/{empleadoId}") {
        fun passLimpiezaId(empleadoId: String) = "AddLimpiezaScreen/$empleadoId"
    }
    object GetLimpiezaScreen : Screens("GetLimpiezaScreen/{empleadoId}") {
        fun passLimpiezaId(empleadoId: String) = "GetLimpiezaScreen/$empleadoId"
    }
    object AddMayoresScreen : Screens("AddMayoresScreen/{empleadoId}") {
        fun passMayoresId(empleadoId: String) = "AddMayoresScreen/$empleadoId"
    }
    object GetMayoresScreen : Screens("GetMayoresScreen/{empleadoId}") {
        fun passMayoresId(empleadoId: String) = "GetMayoresScreen/$empleadoId"
    }
    object AddMenoresScreen : Screens("AddMenoresScreen/{empleadoId}") {
        fun passMenoresId(empleadoId: String) = "AddMenoresScreen/$empleadoId"
    }
    object GetMenoresScreen : Screens("GetMenoresScreen/{empleadoId}") {
        fun passMenoresId(empleadoId: String) = "GetMenoresScreen/$empleadoId"
    }
    object AddFavoritosScreen : Screens("AddFavoritosScreen/{empleadoId}") {
        fun passFavoritosId(empleadoId: String) = "AddFavoritosScreen/$empleadoId"
    }
    object GetFavoritosScreen : Screens("GetFavoritosScreen/{empleadoId}") {
        fun passFavoritosId(empleadoId: String) = "GetFavoritosScreen/$empleadoId"
    }
    object FavoritosScreen: Screens("FavoritosScreen")
}