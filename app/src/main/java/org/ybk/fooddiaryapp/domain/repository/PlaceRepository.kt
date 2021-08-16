package org.ybk.fooddiaryapp.domain.repository

import org.ybk.fooddiaryapp.data.model.place.PlaceResponse

interface PlaceRepository {
    suspend fun searchPlace(keyword: String): PlaceResponse
}