package org.ybk.fooddiaryapp.data.repository.diary.datasourceimpl

import io.reactivex.Completable
import io.reactivex.Single
import org.ybk.fooddiaryapp.data.db.DiaryDao
import org.ybk.fooddiaryapp.data.model.diary.Diary
import org.ybk.fooddiaryapp.data.repository.diary.datasource.DiaryLocalDataSource
import javax.inject.Inject


class DiaryLocalDataSourceImpl @Inject constructor(
    private val diaryDao: DiaryDao
): DiaryLocalDataSource {

    override fun getDiaryAll(email: String): Single<List<Diary>> {
        return diaryDao.getDiaryAll(email)
    }

    override fun getOpenDiaryAll(open: String): Single<List<Diary>> {
        return diaryDao.getOpenDiaryAll(open)
    }

    override fun getDiary(id: String): Single<Diary> {
        return diaryDao.getDiary(id)
    }

    override fun insertDiaries(diaryList: List<Diary>): Completable {
        return diaryDao.insertDiaries(diaryList)
    }

    override fun insertDiary(diary: Diary): Completable {
        return diaryDao.insertDiary(diary)
    }

    override fun updateDiary(diary: Diary): Completable {
        return diaryDao.updateDiary(diary)
    }

    override fun deleteDiary(diary: Diary): Completable {
        return diaryDao.deleteDiary(diary)
    }
}