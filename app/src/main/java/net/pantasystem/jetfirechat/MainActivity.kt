package net.pantasystem.jetfirechat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.composethemeadapter.MdcTheme
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import com.google.firebase.ktx.initialize
import kotlinx.coroutines.ExperimentalCoroutinesApi
import net.pantasystem.jetfirechat.models.MessageView
import net.pantasystem.jetfirechat.models.User
import net.pantasystem.jetfirechat.ui.pages.App
import net.pantasystem.jetfirechat.ui.pages.MessagingPage
import net.pantasystem.jetfirechat.ui.theme.JetFireChatTheme
import net.pantasystem.jetfirechat.viewmodel.MessagesViewModel
import net.pantasystem.jetfirechat.viewmodel.RoomsViewModel
import java.util.*

@ExperimentalCoroutinesApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Firebase.initialize(applicationContext)
        ViewModelProvider(this, MessagesViewModel.Factory())[MessagesViewModel::class.java]
        ViewModelProvider(this)[RoomsViewModel::class.java]

        setContent {
            MdcTheme {

                val users = listOf(
                    User(
                        id = "hoge",
                        username = "hogehoge",
                        profileUrl = "https://pbs.twimg.com/profile_images/1317853360364515364/OIaBg_it_400x400.jpg",
                    ),
                    User(
                        id = "piyo",
                        username = "piyopiyo",
                        profileUrl = "https://pbs.twimg.com/profile_images/1317853360364515364/OIaBg_it_400x400.jpg",
                    ),
                    User(
                        id = "fuga",
                        username = "fugafuga",
                        profileUrl = "https://pbs.twimg.com/profile_images/1317853360364515364/OIaBg_it_400x400.jpg",
                    ),
                )

                val messageTextList = listOf(
                    "こんにちは",
                    "今日はいい天気ですね",
                    "まだ夜だが？",
                    "えぇ・・UTC何？",
                    "ほげほげ",
                    "え？何",
                    "早く家に帰りたいな",
                    "わかりみを感じる",
                    "と言うかこれテスト文章なのでは?",
                    "ほげほげ",
                    "え？何",
                    "早く家に帰りたいな",
                    "わかりみを感じる",
                    "と言うかこれテスト文章なのでは?",
                    "ほげほげ",
                    "え？何",
                    "早く家に帰りたいな",
                    "わかりみを感じる",
                    "と言うかこれテスト文章なのでは?"
                )
                val messages = messageTextList.mapIndexed { index, s ->
                    MessageView(
                        id = "id",
                        text = s,
                        userId = users[index % users.size].id,
                        user = users[index % users.size],
                        createdAt = Date(),
                        updatedAt = Date()
                    )
                }

                App()
            }

        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetFireChatTheme {
        Greeting("Android")
    }
}