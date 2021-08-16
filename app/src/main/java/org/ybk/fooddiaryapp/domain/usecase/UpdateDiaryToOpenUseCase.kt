package org.ybk.fooddiaryapp.domain.usecase

import org.ybk.fooddiaryapp.data.model.diary.Diary
import org.ybk.fooddiaryapp.data.model.etc.TaskResponse
import org.ybk.fooddiaryapp.domain.repository.DiaryRepository
import javax.inject.Inject

class UpdateDiaryToOpenUseCase @Inject constructor(
    private val diaryRepository: DiaryRepository
) {
    suspend fun execute(diary: Diary): TaskResponse = diaryRepository.updateDiaryToOpenOrHideInFirestoreDB(diary)
}