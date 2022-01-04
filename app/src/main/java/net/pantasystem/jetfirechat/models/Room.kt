package net.pantasystem.jetfirechat.models

import com.google.firebase.firestore.DocumentId

data class Room (@DocumentId val id: String, val name: String) {
    constructor() : this("", "")
}