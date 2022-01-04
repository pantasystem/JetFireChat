package net.pantasystem.jetfirechat.ui.pages

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract

@Composable
fun AccountPage() {
    val signInLauncher = rememberLauncherForActivityResult(contract = FirebaseAuthUIActivityResultContract(),) {

    }
    Scaffold(
        topBar = {
            TopAppBar {
                Text("アカウント")
            }
        }
    ) {
        Column(
            Modifier.fillMaxSize()
        ) {
            TextButton(
                onClick = {
                    val providers = arrayListOf(
                        AuthUI.IdpConfig.GoogleBuilder().build()
                    )
                    val ui = AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build()
                    signInLauncher.launch(ui)
                }
            ) {
                Text("ログイン")
            }
        }
    }
}