package com.dani.ftprcar.ui
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.dani.ftprcar.auth.AuthViewModel
import com.google.firebase.auth.PhoneAuthProvider

@Composable
fun LoginScreen(vm: AuthViewModel, onLogged:()->Unit) {
    val ctx = LocalContext.current
    var phone by remember { mutableStateOf("+55 11 91234-5678") }
    var code  by remember { mutableStateOf("123456") }

    Column(Modifier.padding(24.dp)) {
        Text("Login por Telefone (teste)")
        OutlinedTextField(phone, { phone = it }, label={ Text("Telefone") })
        OutlinedTextField(code,  { code  = it }, label={ Text("Código") })

        Button(onClick = {
            val fakeVerificationId = "TEST_VERID"
            val cred = PhoneAuthProvider.getCredential(fakeVerificationId, code)
            vm.signIn(cred) { e -> Toast.makeText(ctx, e.message, Toast.LENGTH_LONG).show() }
        }) { Text("Entrar") }
    }
    val uid by vm.uid.collectAsState()
    LaunchedEffect(uid) { if (uid != null) onLogged() }
}
