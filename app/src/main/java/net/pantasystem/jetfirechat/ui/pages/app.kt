package net.pantasystem.jetfirechat.ui.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.ExperimentalCoroutinesApi

data class NavItem(
    val label: String,
    val route: String,
    val icon: ImageVector
)
@ExperimentalCoroutinesApi
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
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            if(navItems.any { navItem -> currentDestination?.hierarchy?.any { it.route == navItem.route } == true }) {
                BottomNavigation {

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
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
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
                composable("rooms/create") {
                    RoomEditor(navController = navController)
                }
                composable(
                    "rooms/{roomId}",
                    arguments = listOf(
                        navArgument("roomId") { type = NavType.StringType }
                    )
                ) {
                    val roomId = it.arguments?.getString("roomId")
                    MessagingPage(roomId!!)
                }
            }
        }

    }

}