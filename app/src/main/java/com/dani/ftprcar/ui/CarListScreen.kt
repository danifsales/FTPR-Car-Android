package com.dani.ftprcar.ui
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dani.ftprcar.auth.AuthViewModel
import com.dani.ftprcar.data.Car

@Composable
fun CarListScreen(
    vm: CarListViewModel,
    authVm: AuthViewModel,
    onNew:()->Unit,
    onMap:(Car)->Unit
) {
    Scaffold(
        topBar = { TopBarLogout(authVm) },
        floatingActionButton = { FloatingActionButton(onClick = onNew){ Text("+") } }
    ) { pad ->
        LazyColumn(Modifier.padding(pad)) {
            items(vm.items.value) { car ->
                Card(Modifier.padding(12.dp)) {
                    Row(Modifier.padding(12.dp)) {
                        AsyncImage(model = car.imageUrl, contentDescription = null, modifier = Modifier.size(96.dp))
                        Spacer(Modifier.width(12.dp))
                        Column(Modifier.weight(1f)) {
                            Text("${car.name} • ${car.year}", style = MaterialTheme.typography.titleMedium)
                            Text(car.licence)
                            Text("(${car.place.lat}, ${car.place.long})")
                            TextButton(onClick = { onMap(car) }) { Text("Ver no mapa") }
                        }
                    }
                }
            }
        }
    }
}
