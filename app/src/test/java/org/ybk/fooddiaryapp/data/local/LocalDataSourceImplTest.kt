package org.ybk.fooddiaryapp.data.local

import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.ybk.fooddiaryapp.data.local.dao.DiaryDao
import org.ybk.fooddiaryapp.data.local.entity.Diary

@RunWith(MockitoJUnitRunner::class)
class LocalDataSourceImplTest {

    @Mock
    private lateinit var diaryDao: DiaryDao

    private lateinit var local: LocalDataSource

    private lateinit var email: String
    private lateinit var diaryList: ArrayList<Diary>
    private lateinit var diary: Diary
    private lateinit var open: String
    private lateinit var id: String

    @Before
    fun setUp() {
        local = LocalDataSourceImpl(diaryDao)
        email = "ybg1485@gmail.com"
        diary = Diary()
        open = ""
        id = ""
        diaryList = ArrayList<Diary>()
    }

    @Test
    fun testIfDaoGetDiaryAllIsCalled() {
        local.getDiaryAll(email)
        Mockito.verify(diaryDao).getDiaryAll(email)
    }

    @Test
    fun testIfDaoGetOpenDiaryAllIsCalled() {
        local.getOpenDiaryAll(open)
        Mockito.verify(diaryDao).getOpenDiaryAll(open)
    }

    @Test
    fun testIfDaoGetDiaryIsCalled() {
        local.getDiary(id)
        Mockito.verify(diaryDao).getDiary(id)
    }

    @Test
    fun testIfInsertDiariesIsCalled() {
        local.insertDiaries(diaryList)
        Mockito.verify(diaryDao).insertDiaries(diaryList)
    }

    @Test
    fun testIfInsertDiaryIsCalled() {
        local.insertDiary(diary)
        Mockito.verify(diaryDao).insertDiary(diary)
    }

    @Test
    fun testIfUpdateDiaryIsCalled() {
        local.updateDiary(diary)
        Mockito.verify(diaryDao).updateDiary(diary)
    }

    @Test
    fun testIfDeleteDiaryIsCalled() {
        local.deleteDiary(diary)
        Mockito.verify(diaryDao).deleteDiary(diary)
    }
}