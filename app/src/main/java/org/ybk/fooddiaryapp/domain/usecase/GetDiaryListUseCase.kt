package org.ybk.fooddiaryapp.domain.usecase

import org.ybk.fooddiaryapp.data.model.etc.QueryResponse
import org.ybk.fooddiaryapp.domain.repository.DiaryRepository
import javax.inject.Inject

class GetDiaryListUseCase @Inject constructor(
    private val diaryRepository: DiaryRepository
) {
    suspend fun execute(email: String): QueryResponse {
        return diaryRepository.getDiaryListFromFirestoreDB(email)
    }
}