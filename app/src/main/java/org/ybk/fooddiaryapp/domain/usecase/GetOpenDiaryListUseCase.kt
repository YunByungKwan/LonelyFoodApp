package org.ybk.fooddiaryapp.domain.usecase

import org.ybk.fooddiaryapp.data.model.etc.QueryResponse
import org.ybk.fooddiaryapp.domain.repository.DiaryRepository
import javax.inject.Inject

class GetOpenDiaryListUseCase @Inject constructor(
    private val diaryRepository: DiaryRepository
) {
    suspend fun execute(open: String): QueryResponse {
        return diaryRepository.getOpenDiaryListFromFirestoreDB(open)
    }
}