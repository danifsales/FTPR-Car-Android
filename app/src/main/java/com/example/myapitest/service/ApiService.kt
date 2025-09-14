package com.example.myapitest.service

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.DELETE
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Body
import com.example.myapitest.model.Car

interface ApiService {
    @GET("car")
    suspend fun getCars(): List<Car>

    @GET("car/{id}")
    suspend fun getCar(@Path("id") id: String): Car

    @POST("car")
    suspend fun addCar(@Body car: Car): Car

    @PATCH("car/{id}")
    suspend fun updateCar(@Path("id") id: String, @Body car: Car): Car

    @DELETE("car/{id}")
    suspend fun deleteCar(@Path("id") id: String)
}
