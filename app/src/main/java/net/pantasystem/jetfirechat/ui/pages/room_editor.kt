package net.pantasystem.jetfirechat.ui.pages

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.pantasystem.jetfirechat.models.Room
import net.pantasystem.jetfirechat.viewmodel.RoomsViewModel

@Composable
fun RoomEditor(navController: NavController, roomsViewModel: RoomsViewModel = viewModel()) {

    var text: String by remember {
        mutableStateOf("")
    }

    Scaffold(
        topBar = {
            TopAppBar() {
                Text("部屋作成")
            }
        },
    ) {
        val scrollState = rememberScrollState()
        val coroutineScope = rememberCoroutineScope()

        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .verticalScroll(scrollState)
            ) {
                TextField(
                    text,
                    onValueChange = {
                        text = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text("部屋名を入力")
                    }

                )
            }
            TextButton(
                onClick = {
                    coroutineScope.launch(Dispatchers.IO) {
                        roomsViewModel.create(
                            Room(
                                "", text
                            )
                        )
                        navController.navigateUp()
                    }
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("作成する")
            }
        }

    }
}