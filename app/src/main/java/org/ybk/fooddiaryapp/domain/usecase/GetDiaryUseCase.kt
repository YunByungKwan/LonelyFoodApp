package org.ybk.fooddiaryapp.domain.usecase

import org.ybk.fooddiaryapp.data.model.etc.DatabaseResponse
import org.ybk.fooddiaryapp.data.model.etc.EventResponse
import org.ybk.fooddiaryapp.data.model.etc.ValueEventResponse
import org.ybk.fooddiaryapp.domain.repository.DiaryRepository
import javax.inject.Inject

class GetDiaryUseCase(
    private val diaryRepository: DiaryRepository
) {
    suspend fun execute(id: String): DatabaseResponse = diaryRepository.getDiary(id)
}