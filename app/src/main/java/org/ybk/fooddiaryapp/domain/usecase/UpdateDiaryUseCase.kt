package org.ybk.fooddiaryapp.domain.usecase

import org.ybk.fooddiaryapp.domain.repository.DiaryRepository
import javax.inject.Inject

class UpdateDiaryUseCase @Inject constructor(private val diaryRepository: DiaryRepository) {
}