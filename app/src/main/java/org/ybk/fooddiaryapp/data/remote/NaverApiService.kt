package org.ybk.fooddiaryapp.data.remote

import io.reactivex.Single
import org.ybk.fooddiaryapp.data.local.entity.PlaceRes
import retrofit2.http.GET
import retrofit2.http.Query

interface NaverApiService {
    @GET("/v1/search/local.json")
    fun searchPlace(
        @Query("query") query: String,
        @Query("display") display: Int,
        @Query("start") start: Int,
        @Query("sort") sort: String
    ): Single<PlaceRes>
}



