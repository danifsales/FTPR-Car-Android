package com.dani.ftprcar.ui
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dani.ftprcar.data.Car
import com.dani.ftprcar.data.CarRepository
import kotlinx.coroutines.launch

class CarListViewModel(private val repo: CarRepository = CarRepository()) : ViewModel() {
    var items = mutableStateOf<List<Car>>(emptyList())
    fun load() = viewModelScope.launch { items.value = repo.list() }
}
