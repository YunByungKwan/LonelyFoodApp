package org.ybk.fooddiaryapp.data.repository.place.datasourceimpl

import org.ybk.fooddiaryapp.data.api.NaverApiService
import org.ybk.fooddiaryapp.data.model.place.PlaceRes
import org.ybk.fooddiaryapp.data.repository.place.datasource.PlaceRemoteDataSource

class PlaceRemoteDataSourceImpl(
    private val naverApiService: NaverApiService
    ): PlaceRemoteDataSource {

    override suspend fun searchPlace(keyword: String): PlaceRes {
        return naverApiService.searchPlace(keyword, 5, 1, "random")
    }
}