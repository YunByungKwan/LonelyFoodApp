package org.ybk.fooddiaryapp.data.repository.place

import org.ybk.fooddiaryapp.data.model.place.PlaceResponse
import org.ybk.fooddiaryapp.data.repository.place.datasource.PlaceRemoteDataSource
import org.ybk.fooddiaryapp.domain.repository.PlaceRepository
import javax.inject.Inject

class PlaceRepositoryImpl @Inject constructor(
    private val placeRemoteDataSource: PlaceRemoteDataSource
): PlaceRepository {
    override suspend fun searchPlace(keyword: String): PlaceResponse {
        return placeRemoteDataSource.searchPlace(keyword)
    }
}