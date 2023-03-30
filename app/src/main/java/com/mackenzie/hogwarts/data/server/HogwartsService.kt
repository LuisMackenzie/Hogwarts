package com.mackenzie.hogwarts.data.server

import com.mackenzie.hogwarts.data.server.model.DummyResponse
import com.mackenzie.hogwarts.data.server.model.HousesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HogwartsService {

    @GET("/Houses")
    suspend fun getHogwartsHouses(): HousesResponse

    @GET("/Houses/{id}")
    suspend fun getHouse(
        @Path("id") id : String
    ): DummyResponse
}