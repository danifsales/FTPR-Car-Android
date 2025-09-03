package com.dani.ftprcar.ui
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.dani.ftprcar.data.Car
import com.dani.ftprcar.data.CarRepository
import com.dani.ftprcar.data.Place

class CarFormViewModel(private val repo: CarRepository = CarRepository()) : ViewModel() {
    var imageUrl = mutableStateOf<String?>(null)
    var year = mutableStateOf("2020/2020")
    var name = mutableStateOf("")
    var licence = mutableStateOf("")
    var lat = mutableStateOf<Double?>(null)
    var long = mutableStateOf<Double?>(null)
    suspend fun save(): Boolean {
        val url = imageUrl.value ?: return false
        repo.create(
            Car(imageUrl = url, year = year.value, name = name.value, licence = licence.value,
                place = Place(lat.value ?: 0.0, long.value ?: 0.0))
        )
        return true
    }
}
