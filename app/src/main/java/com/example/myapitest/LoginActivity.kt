package com.example.myapitest

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.myapitest.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    private lateinit var oneTapClient: SignInClient
    private lateinit var oneTapLauncher: ActivityResultLauncher<IntentSenderRequest>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        oneTapClient = Identity.getSignInClient(this)

        setupOneTapLauncher()
        setupView()
        verifyLoggedUser()
    }

    private fun setupView() {
        binding.googleSignInButton.setOnClickListener { startOneTapSignIn() }
    }

    private fun setupOneTapLauncher() {
        oneTapLauncher = registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                try {
                    val credential = oneTapClient.getSignInCredentialFromIntent(result.data)
                    val idToken = credential.googleIdToken
                    if (idToken != null) {
                        firebaseAuthWithGoogle(idToken)
                    } else {
                        Toast.makeText(this, "Não foi possível obter o ID Token do Google.", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Log.e("LoginActivity", "Falha ao obter credencial do One Tap", e)
                    Toast.makeText(this, "Falha ao concluir o login.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun startOneTapSignIn() {

        val serverClientId = getString(R.string.default_web_client_id)

        val signInRequest = BeginSignInRequest.Builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(serverClientId)
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()

        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener { result ->
                val req = IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                oneTapLauncher.launch(req)
            }
            .addOnFailureListener { e ->
                Log.w("LoginActivity", "One Tap não disponível: ${e.message}")
                Toast.makeText(this, "Não foi possível iniciar o login do Google.", Toast.LENGTH_SHORT).show()
            }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                navigateToMainActivity()
            } else {
                Toast.makeText(this, "Erro com o Login usando o Google", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun verifyLoggedUser() {
        if (auth.currentUser != null) navigateToMainActivity()
    }

    private fun navigateToMainActivity() {
        startActivity(MainActivity.newIntent(this))
        finish()
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, LoginActivity::class.java)
    }
}
