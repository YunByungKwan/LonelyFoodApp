package org.ybk.fooddiaryapp.ui.diary

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import org.ybk.fooddiaryapp.TestUtils.getOrAwaitValue
import org.ybk.fooddiaryapp.data.repository.diary.DiaryRepositoryImpl
import org.ybk.fooddiaryapp.data.model.etc.DataResponse
import org.ybk.fooddiaryapp.data.model.diary.Diary
import org.ybk.fooddiaryapp.presentation.diary.DiaryViewModel

@RunWith(MockitoJUnitRunner::class)
class DiaryViewModelTest {
    @get:Rule
    var instantExecutorRule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repo: DiaryRepositoryImpl

    private lateinit var vm: DiaryViewModel

    private lateinit var email: String
    private lateinit var localRes: Single<List<Diary>>
    private lateinit var diaryList: ArrayList<Diary>
    private lateinit var diary: Diary

    @Before
    fun set_up() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler{
            Schedulers.trampoline() }
        vm = DiaryViewModel(repo)
        email = "ybg1485@gmail.com"
        diaryList = ArrayList<Diary>()
        diary = Diary()
        localRes = Single.just(diaryList)

        mocking()
    }

    private fun mocking() {
        Mockito.`when`(repo.getDiaryAll(email))
            .thenReturn(DataResponse(localRes, null))
        Mockito.`when`(repo.insertDiaryToLocalDB(diary))
            .thenReturn(Completable.complete())
    }

    @Test
    fun testIfLiveDataHasValue() {
        vm.getDiaryAll(email)
        vm.diaryList.getOrAwaitValue()
        Assert.assertThat(vm.diaryList.value, CoreMatchers.`is`(diaryList))
    }

    @Test
    fun testIfRepoGetDiaryAllIsCalled_2_times() {
        vm.getDiaryAll(email)
        verify(repo, times(2)).getDiaryAll(email)
    }

    @Test
    fun testGetDiaryAll_local() {
        vm.getDiaryAll(email)
        repo.getDiaryAll(email)
            .getLocalResponse()!!
            .test()
            .assertSubscribed()
            .assertComplete()
            .assertNoErrors()
    }

    @Test
    fun testIfRepoInsertDiaryToLocalDBIsCalled() {
        vm.insertDiaryToLocalDB(diary)
        verify(repo).insertDiaryToLocalDB(diary)
    }

    @Test
    fun testInsertDiaryToLocalDB() {
        vm.insertDiaryToLocalDB(diary)
        repo.insertDiaryToLocalDB(diary)
            .test()
            .assertSubscribed()
            .assertComplete()
            .assertNoErrors()
    }
}