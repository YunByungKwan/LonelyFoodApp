package org.ybk.fooddiaryapp.data.repository.place

import org.ybk.fooddiaryapp.data.model.place.PlaceRes
import org.ybk.fooddiaryapp.data.repository.place.datasource.PlaceRemoteDataSource
import org.ybk.fooddiaryapp.domain.repository.PlaceRepository

class PlaceRepositoryImpl(
    private val placeRemoteDataSource: PlaceRemoteDataSource
): PlaceRepository {

    override suspend fun searchPlace(keyword: String): PlaceRes {
        return placeRemoteDataSource.searchPlace(keyword)
    }
}