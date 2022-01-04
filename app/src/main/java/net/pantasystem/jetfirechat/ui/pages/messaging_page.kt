package net.pantasystem.jetfirechat.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import net.pantasystem.jetfirechat.models.MessageView
import net.pantasystem.jetfirechat.viewmodel.MessagesViewModel

@ExperimentalCoroutinesApi
@Composable
fun MessagingPage(
    myId: String,
    messageViewModel: MessagesViewModel = viewModel()
) {

    var text: String by remember {
        mutableStateOf("")
    }

    val messages by messageViewModel.messages.collectAsState(initial = emptyList())
    Scaffold(
        topBar = {
            TopAppBar {
                Text("メッセージ一覧")
            }
        }
    ) {
        Column {
            Messages(
                messages = messages,
                modifier = Modifier.weight(1f),
                myId = myId,
                onAvatarClicked = {}
            )

            Surface {
                Row {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp)
                            .height(52.dp)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.CenterStart
                    ) {

                        if(text.isEmpty()) {
                            Text("本文を入力")
                        }
                        BasicTextField(
                            value = text, onValueChange = { t -> text = t },
                            modifier = Modifier
                                .fillMaxWidth(),
                            maxLines = 1,
                            textStyle = LocalTextStyle.current.copy(color = Color.White),
                            cursorBrush = SolidColor(LocalContentColor.current)
                        )
                    }
                    IconButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier.size(52.dp)
                    ) {
                        Icon(Icons.Default.Send, contentDescription = "送信")
                    }
                }
            }

        }
    }
}

@Composable
fun Messages(
    messages: List<MessageView>,
    myId: String,
    onAvatarClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    Box(modifier = modifier) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(messages.size) { index ->
                MessageItem(
                    message = messages[index],
                    myId = myId,
                    onAvatarClicked = onAvatarClicked
                )
            }
        }
    }

}


@Composable
fun MessageItem(
    message: MessageView,
    myId: String,
    onAvatarClicked: (String) -> Unit,
) {
    if (message.userId == myId) {
        Box(
            contentAlignment = Alignment.TopEnd,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row {
                Column {
                    Text(
                        message.user?.username ?: "",
                        modifier = Modifier.align(Alignment.End)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Surface(
                        shape = RoundedCornerShape(20.dp, 4.dp, 20.dp, 20.dp),
                        color = MaterialTheme.colors.primary
                    ) {
                        Text(
                            message.text,
                            modifier = Modifier
                                .padding(8.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(4.dp))
                Image(
                    rememberImagePainter(message.user?.profileUrl),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clickable {
                            onAvatarClicked(message.userId)
                        }
                        .size(42.dp)
                        .clip(CircleShape)
                )
            }

        }

    } else {
        Row {
            Image(
                rememberImagePainter(message.user?.profileUrl),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clickable {
                        onAvatarClicked(message.userId)
                    }
                    .size(42.dp)
                    .clip(CircleShape)
                    .align(Alignment.Top)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Column {
                Text(message.user?.username ?: "")
                Spacer(modifier = Modifier.height(4.dp))
                Surface(
                    shape = RoundedCornerShape(4.dp, 20.dp, 20.dp, 20.dp),
                    color = MaterialTheme.colors.primary.copy(alpha = 0.25f),

                    ) {
                    Text(
                        message.text,
                        modifier = Modifier
                            .padding(8.dp)
                    )
                }
            }

        }
    }

}