package net.pantasystem.jetfirechat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.android.material.composethemeadapter.MdcTheme
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import kotlinx.coroutines.ExperimentalCoroutinesApi
import net.pantasystem.jetfirechat.ui.pages.App
import net.pantasystem.jetfirechat.ui.theme.JetFireChatTheme
import net.pantasystem.jetfirechat.viewmodel.MessagesViewModel
import net.pantasystem.jetfirechat.viewmodel.RoomsViewModel

@ExperimentalCoroutinesApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Firebase.initialize(applicationContext)
        ViewModelProvider(this, MessagesViewModel.Factory())[MessagesViewModel::class.java]
        ViewModelProvider(this)[RoomsViewModel::class.java]

        setContent {
            MdcTheme {
                ProvideWindowInsets {
                    App()

                }
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