package org.ybk.fooddiaryapp.domain.repository

import org.ybk.fooddiaryapp.data.model.place.PlaceRes

interface PlaceRepository {
    suspend fun searchPlace(keyword: String): PlaceRes
}