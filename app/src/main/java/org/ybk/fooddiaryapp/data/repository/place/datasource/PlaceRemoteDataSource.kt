package org.ybk.fooddiaryapp.data.repository.place.datasource

import org.ybk.fooddiaryapp.data.model.place.PlaceResponse

interface PlaceRemoteDataSource {
    suspend fun searchPlace(keyword: String): PlaceResponse
}