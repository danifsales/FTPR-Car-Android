package com.dani.ftprcar.auth
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val _uid = MutableStateFlow(auth.currentUser?.uid)
    val uid = _uid.asStateFlow()

    fun signIn(credential: PhoneAuthCredential, onError:(Throwable)->Unit = {}) {
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) _uid.value = auth.currentUser?.uid else onError(it.exception ?: RuntimeException())
        }
    }
    fun signOut() { auth.signOut(); _uid.value = null }
}
