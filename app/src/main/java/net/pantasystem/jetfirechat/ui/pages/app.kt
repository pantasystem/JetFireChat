package net.pantasystem.jetfirechat.ui.pages

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

data class NavItem(
    val label: String,
    val route: String,
    val icon: ImageVector
)
@Composable
fun App() {
    val navController = rememberNavController()

//    val navItems = listOf(
//        "Home" to Icons.Default.Home,
//        "Account" to Icons.Default.AccountCircle
//    )
    val navItems = listOf(
        NavItem(
            "Home",
            "home",
            Icons.Default.Home
        ),
        NavItem(
            "Account",
            "account",
            Icons.Default.AccountCircle
        )
    )
    Scaffold(
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                navItems.forEach { item ->
                    BottomNavigationItem(
                        selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                        onClick = {
                             navController.navigate(item.route) {
                                 popUpTo(navController.graph.findStartDestination().id) {
                                     saveState = true
                                 }
                                 launchSingleTop = true
                                 restoreState = true
                             }
                        },
                        icon = {
                            Icon(item.icon, contentDescription = item.label)
                        },
                        label = {
                            Text(item.label)
                        }
                    )
                }
            }
        }
    ) {
        NavHost(
            navController = navController,
            "home"
        ) {
            composable("home") {
                HomePage(navController = navController)
            }
            composable("account") {
                AccountPage()
            }
        }
    }

}