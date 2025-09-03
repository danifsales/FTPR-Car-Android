package com.dani.ftprcar.ui

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dani.ftprcar.data.Car
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@Composable
fun MapScreen(car: Car, onBack:()->Unit) {
    val pos = LatLng(car.place.lat, car.place.long)
    val camera = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(pos, 15f)
    }
    Scaffold(topBar = { TopAppBar(title={ Text(car.name) }) }) { pad ->
        GoogleMap(Modifier.padding(pad).fillMaxSize(), cameraPositionState = camera) {
            Marker(state = MarkerState(pos), title = car.name, snippet = car.licence)
        }
    }
}