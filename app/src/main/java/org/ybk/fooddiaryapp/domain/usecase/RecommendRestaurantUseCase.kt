package org.ybk.fooddiaryapp.domain.usecase

import android.util.Log
import org.ybk.fooddiaryapp.data.model.diary.Diary
import org.ybk.fooddiaryapp.data.model.etc.TaskResponse
import org.ybk.fooddiaryapp.domain.repository.DiaryRepository

class RecommendRestaurantUseCase(
    private val diaryRepository: DiaryRepository
) {
    suspend fun execute(diary: Diary): TaskResponse {
        Log.d("TAG2", "RecommendRestaurantUseCase - execute()")
        return diaryRepository.addDiaryToRecommendList(diary)
    }
}