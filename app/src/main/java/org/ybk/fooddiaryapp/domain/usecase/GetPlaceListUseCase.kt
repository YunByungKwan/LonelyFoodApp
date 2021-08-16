package org.ybk.fooddiaryapp.domain.usecase

import org.ybk.fooddiaryapp.data.model.place.PlaceResponse
import org.ybk.fooddiaryapp.domain.repository.PlaceRepository
import javax.inject.Inject

class GetPlaceListUseCase @Inject constructor(
    private val placeRepository: PlaceRepository
) {
    suspend fun execute(keyword: String): PlaceResponse {
        return placeRepository.searchPlace(keyword)
    }
}