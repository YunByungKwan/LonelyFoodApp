package org.ybk.fooddiaryapp.domain.usecase

import org.ybk.fooddiaryapp.data.model.place.PlaceRes
import org.ybk.fooddiaryapp.domain.repository.DiaryRepository
import org.ybk.fooddiaryapp.domain.repository.PlaceRepository
import javax.inject.Inject

class GetPlaceListUseCase(
    private val placeRepository: PlaceRepository) {

    suspend fun execute(keyword: String): PlaceRes = placeRepository.searchPlace(keyword)
}