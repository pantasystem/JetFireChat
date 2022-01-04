package net.pantasystem.jetfirechat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import net.pantasystem.jetfirechat.models.Room
import net.pantasystem.jetfirechat.util.asFlow
import net.pantasystem.jetfirechat.util.asSuspend

class RoomsViewModel : ViewModel(){



    private val db: FirebaseFirestore
        get() = Firebase.firestore


    @ExperimentalCoroutinesApi
    val rooms = db.collection("rooms")
        .asFlow()
        .map {
            it.toObjects(Room::class.java)
        }
        .shareIn(viewModelScope, started = SharingStarted.Eagerly, replay = 1)


    suspend fun create(room: Room) {
        db.collection("rooms")
            .add(room)
            .asSuspend()
    }


}