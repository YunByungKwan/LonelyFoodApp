package org.ybk.fooddiaryapp.data.local

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.ybk.fooddiaryapp.data.db.DiaryDao
import org.ybk.fooddiaryapp.data.model.diary.Diary
import org.ybk.fooddiaryapp.data.repository.diary.datasource.DiaryLocalDataSource
import org.ybk.fooddiaryapp.data.repository.diary.datasourceimpl.DiaryLocalDataSourceImpl

@RunWith(MockitoJUnitRunner::class)
class DiaryLocalDataSourceImplTest {

    @Mock
    private lateinit var diaryDao: DiaryDao

    private lateinit var diaryLocal: DiaryLocalDataSource

    private lateinit var email: String
    private lateinit var diaryList: ArrayList<Diary>
    private lateinit var diary: Diary
    private lateinit var open: String
    private lateinit var id: String

    @Before
    fun setUp() {
        diaryLocal = DiaryLocalDataSourceImpl(diaryDao)
        email = "ybg1485@gmail.com"
        diary = Diary()
        open = ""
        id = ""
        diaryList = ArrayList<Diary>()
    }

    @Test
    fun testIfDaoGetDiaryAllIsCalled() {
        diaryLocal.getDiaryAll(email)
        Mockito.verify(diaryDao).getDiaryAll(email)
    }

    @Test
    fun testIfDaoGetOpenDiaryAllIsCalled() {
        diaryLocal.getOpenDiaryAll(open)
        Mockito.verify(diaryDao).getOpenDiaryAll(open)
    }

    @Test
    fun testIfDaoGetDiaryIsCalled() {
        diaryLocal.getDiary(id)
        Mockito.verify(diaryDao).getDiary(id)
    }

    @Test
    fun testIfInsertDiariesIsCalled() {
        diaryLocal.insertDiaries(diaryList)
        Mockito.verify(diaryDao).insertDiaries(diaryList)
    }

    @Test
    fun testIfInsertDiaryIsCalled() {
        diaryLocal.insertDiary(diary)
        Mockito.verify(diaryDao).insertDiary(diary)
    }

    @Test
    fun testIfUpdateDiaryIsCalled() {
        diaryLocal.updateDiary(diary)
        Mockito.verify(diaryDao).updateDiary(diary)
    }

    @Test
    fun testIfDeleteDiaryIsCalled() {
        diaryLocal.deleteDiary(diary)
        Mockito.verify(diaryDao).deleteDiary(diary)
    }
}