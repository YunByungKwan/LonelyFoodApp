package org.ybk.fooddiaryapp.domain.usecase

import androidx.lifecycle.MutableLiveData
import org.ybk.fooddiaryapp.data.model.diary.Diary
import org.ybk.fooddiaryapp.data.model.etc.QueryResponse
import org.ybk.fooddiaryapp.domain.repository.DiaryRepository
import org.ybk.fooddiaryapp.util.Status
import javax.inject.Inject

class DeleteDiaryUseCase @Inject constructor(
    private val diaryRepository: DiaryRepository
) {
    fun execute(diary: Diary, status: MutableLiveData<Status>) {
        diaryRepository.deleteDiary(diary, status)
    }

    suspend fun execute2(diary: Diary): QueryResponse {
        return diaryRepository.deleteDiaryFromFirestoreDB(diary)
    }
}