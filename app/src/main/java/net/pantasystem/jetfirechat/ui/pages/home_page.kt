package net.pantasystem.jetfirechat.ui.pages


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
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
fun HomePage(navController: NavController, roomsViewModel: RoomsViewModel = viewModel()) {

    val rooms by roomsViewModel.rooms.collectAsState(initial = emptyList())
    Scaffold(
        topBar = {
            TopAppBar {
                Text("ホーム")
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(rooms.size) { index ->
                Card(
                    Modifier.padding(16.dp)
                ) {
                    Text(rooms[index].name)
                }
            }
        }
    }
}