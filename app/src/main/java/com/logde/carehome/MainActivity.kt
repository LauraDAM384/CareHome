package com.logde.carehome

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.logde.carehome.navigation.AppNavigation

import com.logde.carehome.ui.theme.CareHomeTheme
import com.logde.carehome.ui.theme.gradientBrush

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CareHomeTheme {
                // Call the main UI
                MainContent()
                }
            }
        }
    }

@Composable
fun MainContent() {
    // Define the diagonal gradient brush


    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 46.dp)
    ) {
        // Apply the gradient background
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = gradientBrush),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Navigation Controller
            val navController = rememberNavController()
            // Pass the NavController to AppNavigation
            AppNavigation(navController = navController)
        }
    }
}



