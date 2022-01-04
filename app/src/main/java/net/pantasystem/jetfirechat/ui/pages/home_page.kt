package net.pantasystem.jetfirechat.ui.pages

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.ExperimentalCoroutinesApi
import net.pantasystem.jetfirechat.viewmodel.RoomsViewModel

@Composable
@ExperimentalCoroutinesApi
@OptIn(ExperimentalMaterialApi::class)
fun HomePage(navController: NavController, roomsViewModel: RoomsViewModel = viewModel()) {

    val rooms by roomsViewModel.rooms.collectAsState(initial = emptyList())
    Scaffold(
        topBar = {
            TopAppBar {
                Text("ホーム")
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("rooms/create")
                }
            ) {
                Icon(Icons.Default.Add, contentDescription = "部屋を追加する")
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(rooms.size) { index ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {
                        navController.navigate("rooms/${rooms[index].id}")
                    }
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                    ) {
                        Text(rooms[index].name)
                    }
                }
            }
        }
    }
}