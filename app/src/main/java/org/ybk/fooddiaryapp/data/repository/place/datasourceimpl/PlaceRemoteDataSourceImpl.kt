package org.ybk.fooddiaryapp.data.repository.place.datasourceimpl

import org.ybk.fooddiaryapp.data.api.NaverApiService
import org.ybk.fooddiaryapp.data.model.place.PlaceResponse
import org.ybk.fooddiaryapp.data.repository.place.datasource.PlaceRemoteDataSource
import javax.inject.Inject

class PlaceRemoteDataSourceImpl @Inject constructor(
    private val naverApiService: NaverApiService
    ): PlaceRemoteDataSource {
    override suspend fun searchPlace(keyword: String): PlaceResponse {
        return naverApiService.searchPlace(keyword, 5, 1, "random")
    }
}