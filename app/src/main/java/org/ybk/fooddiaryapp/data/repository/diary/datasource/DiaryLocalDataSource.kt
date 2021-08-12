package org.ybk.fooddiaryapp.data.repository.diary.datasource

import io.reactivex.Completable
import io.reactivex.Single
import org.ybk.fooddiaryapp.data.model.diary.Diary

interface DiaryLocalDataSource {

    fun getDiaryAll(email: String): Single<List<Diary>>

    fun getOpenDiaryAll(open: String): Single<List<Diary>>

    fun getDiary(id: String): Single<Diary>

    fun insertDiaries(diaryList: List<Diary>): Completable

    fun insertDiary(diary: Diary): Completable

    fun updateDiary(diary: Diary): Completable

    fun deleteDiary(diary: Diary): Completable
}