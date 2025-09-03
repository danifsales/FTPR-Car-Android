package com.dani.ftprcar.data
import retrofit2.http.*

interface CarApi {
    @GET("/car") suspend fun getCars(): List<Car>
    @POST("/car") suspend fun postCar(@Body car: Car): List<Car>
    @DELETE("/car/{id}") suspend fun deleteCar(@Path("id") id: String)
}
