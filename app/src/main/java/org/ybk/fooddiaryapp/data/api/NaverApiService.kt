package org.ybk.fooddiaryapp.data.api

import org.ybk.fooddiaryapp.data.model.place.PlaceRes
import retrofit2.http.GET
import retrofit2.http.Query

interface NaverApiService {
    @GET("/v1/search/local.json")
    suspend fun searchPlace(
        @Query("query") query: String,
        @Query("display") display: Int,
        @Query("start") start: Int,
        @Query("sort") sort: String
    ): PlaceRes
}



