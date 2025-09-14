package com.dani.ftprcar.ui
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import com.dani.ftprcar.auth.AuthViewModel

@Composable
fun TopBarLogout(vm: AuthViewModel) {
    TopAppBar(title = { Text("Carros") }, actions = {
        TextButton(onClick = { vm.signOut() }) { Text("Sair") }
    })
}
