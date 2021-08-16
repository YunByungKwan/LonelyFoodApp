package org.ybk.fooddiaryapp.domain.usecase

import org.ybk.fooddiaryapp.data.model.diary.Diary
import org.ybk.fooddiaryapp.data.model.etc.DatabaseResponse
import org.ybk.fooddiaryapp.data.model.etc.DocumentResponse
import org.ybk.fooddiaryapp.data.model.etc.EventResponse
import org.ybk.fooddiaryapp.data.model.etc.ValueEventResponse
import org.ybk.fooddiaryapp.domain.repository.DiaryRepository
import javax.inject.Inject

class GetDiaryUseCase @Inject constructor(
    private val diaryRepository: DiaryRepository
) {
    suspend fun execute(email: String, registerTime: String): DocumentResponse {
        return diaryRepository.getDiaryFromFirestoreDB(email, registerTime)
    }
}