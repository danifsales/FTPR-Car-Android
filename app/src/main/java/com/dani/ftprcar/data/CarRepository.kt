package com.dani.ftprcar.data
class CarRepository(private val api: CarApi = ApiProvider.api) {
    suspend fun list() = api.getCars()
    suspend fun create(car: Car) = api.postCar(car)
    suspend fun remove(id: String) = api.deleteCar(id)
}
