package net.pantasystem.jetfirechat.ui.pages

import androidx.compose.material.BottomNavigation
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable

@Composable
fun HomePage() {

    val navController = listOf(
        "Home" to Icons.Default.Home,
        "Account" to Icons.Default.AccountCircle
    )
    Scaffold(
        bottomBar = {
            BottomNavigation {

            }
        }
    ) {

    }
}