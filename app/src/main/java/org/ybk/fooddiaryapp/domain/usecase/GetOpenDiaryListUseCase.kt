package org.ybk.fooddiaryapp.domain.usecase

import org.ybk.fooddiaryapp.data.model.etc.DatabaseResponse
import org.ybk.fooddiaryapp.data.model.etc.EventResponse
import org.ybk.fooddiaryapp.data.model.etc.QueryResponse
import org.ybk.fooddiaryapp.data.model.etc.ValueEventResponse
import org.ybk.fooddiaryapp.domain.repository.DiaryRepository
import javax.inject.Inject

class GetOpenDiaryListUseCase(
    private val diaryRepository: DiaryRepository
    ) {

    suspend fun execute(open: String): QueryResponse = diaryRepository.getOpenDiaryListFromFirestoreDB(open)
}