package net.pantasystem.jetfirechat.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import net.pantasystem.jetfirechat.models.Message
import net.pantasystem.jetfirechat.models.MessageView
import net.pantasystem.jetfirechat.models.Room
import net.pantasystem.jetfirechat.models.User
import net.pantasystem.jetfirechat.util.asFlow
import net.pantasystem.jetfirechat.util.asSuspend
import java.util.*

@ExperimentalCoroutinesApi
class MessagesViewModel: ViewModel(){

    @Suppress("UNCHECKED_CAST")
    class Factory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MessagesViewModel() as T
        }
    }

    private val _roomId = MutableStateFlow<String?>(null)

    val messages = _roomId.filterNotNull().flatMapLatest { roomId ->
        Firebase.firestore.collection("rooms").document(roomId)
            .collection("messages")
            .orderBy("createdAt")
            .asFlow().mapNotNull {
                Log.d("MessagesViewModel", "${it?.documents?.map { it.data }}")
                it?.toObjects(Message::class.java)
            }
    }.map {
        it.map {  msg ->
            MessageView(
                id = msg.id,
                createdAt = msg.createdAt,
                updatedAt = msg.updatedAt,
                text = msg.text,
                userId = msg.userId,
                user = msg.userRef?.get()?.asSuspend()?.toObject(User::class.java)
            )
        }
    }.shareIn(viewModelScope, started = SharingStarted.Eagerly, replay = 1)



    val room = _roomId.filterNotNull().flatMapLatest { roomId ->
        Firebase.firestore.collection("rooms").document(roomId)
            .asFlow()
    }.map {
        it?.toObject(Room::class.java)
    }.shareIn(viewModelScope, started = SharingStarted.Eagerly, replay = 1)


    suspend fun create(text: String) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        Log.d("MessagesViewModel", "create text:$text")
        Firebase.firestore.collection("rooms")
            .document(_roomId.value!!)
            .collection("messages")
            .add(Message(
                id = "",
                userId = currentUser!!.uid,
                text = text,
                createdAt = Date(),
                updatedAt = Date(),
                userRef = Firebase.firestore.collection("users").document(currentUser.uid)
            )).asSuspend()

    }

    fun setRoomId(roomId: String) {
        _roomId.value = roomId
    }


}