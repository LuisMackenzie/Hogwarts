package com.mackenzie.hogwarts.data.server

import com.mackenzie.hogwarts.data.server.model.HousesResult
import retrofit2.http.GET
import retrofit2.http.Path

interface HogwartsService {

    @GET("/Houses")
    suspend fun getHogwartsHouses(): List<HousesResult>

    @GET("/Houses/{id}")
    suspend fun getHouse(
        @Path("id") id : String
    ): HousesResult
}