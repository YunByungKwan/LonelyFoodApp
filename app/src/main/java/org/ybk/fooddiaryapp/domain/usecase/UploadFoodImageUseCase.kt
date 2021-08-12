package org.ybk.fooddiaryapp.domain.usecase

import org.ybk.fooddiaryapp.data.model.diary.FoodImage
import org.ybk.fooddiaryapp.data.model.etc.UploadResponse
import org.ybk.fooddiaryapp.domain.repository.DiaryRepository

class UploadFoodImageUseCase(
    private val diaryRepository: DiaryRepository
) {

    suspend fun execute(foodImage: FoodImage): UploadResponse = diaryRepository.uploadFoodImage(foodImage)
}