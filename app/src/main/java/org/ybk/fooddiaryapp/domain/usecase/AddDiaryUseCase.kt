package org.ybk.fooddiaryapp.domain.usecase

import android.util.Log
import org.ybk.fooddiaryapp.data.model.diary.Diary
import org.ybk.fooddiaryapp.data.model.etc.Response
import org.ybk.fooddiaryapp.data.model.etc.TaskResponse
import org.ybk.fooddiaryapp.domain.repository.DiaryRepository
import org.ybk.fooddiaryapp.util.Status

class AddDiaryUseCase(private val diaryRepository: DiaryRepository) {
    // suspend fun execute(diary: Diary): TaskResponse = diaryRepository.addDiary2(diary)
    suspend fun execute3(diary: Diary): TaskResponse {
        Log.d("TAG2", "execute3")
        return diaryRepository.addDiaryToFirestoreDB(diary)
    }
}