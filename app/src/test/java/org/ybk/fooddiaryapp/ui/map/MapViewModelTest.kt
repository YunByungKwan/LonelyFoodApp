package org.ybk.fooddiaryapp.ui.map

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.firebase.database.Query
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.ybk.fooddiaryapp.TestUtils.getOrAwaitValue
import org.ybk.fooddiaryapp.data.DiaryRepositoryImpl
import org.ybk.fooddiaryapp.data.local.entity.DataResponse
import org.ybk.fooddiaryapp.data.local.entity.Diary

@RunWith(MockitoJUnitRunner::class)
class MapViewModelTest {
    @get:Rule
    var instantExecutorRule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repo: DiaryRepositoryImpl

    private lateinit var vm: MapViewModel

    private lateinit var email: String
    private lateinit var diaryList: ArrayList<Diary>
    private lateinit var localRes: Single<List<Diary>>
    private lateinit var dataRes: DataResponse<Single<List<Diary>>, Single<Query>>

    @Before
    fun setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler{ Schedulers.trampoline() }
        vm = MapViewModel(repo)
        email = "ybg1485@gmail.com"
        diaryList = ArrayList<Diary>()
        localRes = Single.just(diaryList)
        dataRes = DataResponse(localRes, null)
        mocking()
    }

    private fun mocking() {
        Mockito.`when`(repo.getDiaryAll(email)).thenReturn(DataResponse(localRes, null))
    }

    @Test
    fun testIfLiveDataHasValue() {
        vm.getDiaryAll(email)
        vm.diaryList.getOrAwaitValue()
        assertThat(vm.diaryList.value, `is`(diaryList))
    }

    @Test
    fun testIfRepoGetDiaryAllIsCalled_2_times() {
        vm.getDiaryAll(email)
        Mockito.verify(repo, Mockito.times(2)).getDiaryAll(email)
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
}