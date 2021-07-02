package org.ybk.fooddiaryapp.ui.share

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.firebase.database.Query
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.hamcrest.CoreMatchers
import org.hamcrest.Matchers.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.junit.MockitoJUnitRunner
import org.ybk.fooddiaryapp.TestUtils.getOrAwaitValue
import org.ybk.fooddiaryapp.data.DiaryRepositoryImpl
import org.ybk.fooddiaryapp.data.local.entity.DataResponse
import org.ybk.fooddiaryapp.data.local.entity.Diary
import org.ybk.fooddiaryapp.util.Constants

@RunWith(MockitoJUnitRunner::class)
class ShareViewModelTest {
    @get:Rule
    var instantExecutorRule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repo: DiaryRepositoryImpl

    private lateinit var vm: ShareViewModel

    private lateinit var diaryList: ArrayList<Diary>
    private lateinit var localRes: Single<List<Diary>>
    private lateinit var dataRes: DataResponse<Single<List<Diary>>, Single<Query>>

    @Before
    fun setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler{
            Schedulers.trampoline() }
        vm = ShareViewModel(repo)
        diaryList = ArrayList<Diary>()
        localRes = Single.just(diaryList)
        dataRes = DataResponse(localRes, null)
        mocking()
    }

    private fun mocking() {
        Mockito.`when`(repo.getOpenDiaryAll(Constants.IS_OPEN))
            .thenReturn(dataRes)
    }

    @Test
    fun testIfLiveDataHasValue() {
        vm.getOpenDiaryAll()
        vm.diaryList.getOrAwaitValue()
        assertThat(vm.diaryList.value, `is`(diaryList))
    }

    @Test
    fun testIfRepoGetOpenDiaryAllIsCalled_2_times() {
        vm.getOpenDiaryAll()
        Mockito.verify(repo, times(2))
            .getOpenDiaryAll(Constants.IS_OPEN)
    }

    @Test
    fun testGetOpenDiaryAll_local() {
        vm.getOpenDiaryAll()
        repo.getOpenDiaryAll(Constants.IS_OPEN)
            .getLocalResponse()!!
            .test()
            .assertSubscribed()
            .assertComplete()
            .assertNoErrors()
    }
}