package com.dani.ftprcar.storage
import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.util.UUID

object StorageHelper {
    suspend fun upload(uri: Uri): String {
        val ref = FirebaseStorage.getInstance().reference.child("cars/${UUID.randomUUID()}.jpg")
        ref.putFile(uri).await()
        return ref.downloadUrl.await().toString()
    }
}
