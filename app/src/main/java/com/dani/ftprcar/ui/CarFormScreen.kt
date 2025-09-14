package com.dani.ftprcar.ui
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.dani.ftprcar.location.LocationHelper
import com.dani.ftprcar.storage.StorageHelper
import kotlinx.coroutines.launch

@Composable
fun CarFormScreen(vm: CarFormViewModel, onSaved:()->Unit) {
    val ctx = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val pick = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) lifecycle.lifecycleScope.launch {
            try { vm.imageUrl.value = StorageHelper.upload(uri); Toast.makeText(ctx,"Imagem OK",Toast.LENGTH_SHORT).show() }
            catch (e:Exception){ Toast.makeText(ctx,e.message,Toast.LENGTH_LONG).show() }
        }
    }
    Column(Modifier.padding(16.dp)) {
        OutlinedTextField(vm.name.value, { vm.name.value = it }, label={Text("Nome")})
        OutlinedTextField(vm.year.value, { vm.year.value = it }, label={Text("Ano")})
        OutlinedTextField(vm.licence.value, { vm.licence.value = it }, label={Text("Placa")})
        Row {
            Button(onClick = { pick.launch("image/*") }) { Text(if (vm.imageUrl.value==null) "Escolher imagem" else "Imagem enviada") }
            Spacer(Modifier.width(12.dp))
            Button(onClick = {
                lifecycle.lifecycleScope.launch {
                    LocationHelper.last(ctx)?.let { (la,lo) -> vm.lat.value=la; vm.long.value=lo; Toast.makeText(ctx,"Local OK",Toast.LENGTH_SHORT).show() }
                }
            }) { Text("Usar minha localização") }
        }
        Spacer(Modifier.height(12.dp))
        Button(onClick = { lifecycle.lifecycleScope.launch { if (vm.save()) onSaved() } }) { Text("Salvar") }
    }
}
