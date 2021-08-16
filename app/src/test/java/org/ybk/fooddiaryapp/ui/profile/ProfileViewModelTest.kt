package org.ybk.fooddiaryapp.ui.profile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.Query
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.hamcrest.CoreMatchers
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import org.ybk.fooddiaryapp.TestUtils.getOrAwaitValue
import org.ybk.fooddiaryapp.data.repository.diary.DiaryRepositoryImpl
import org.ybk.fooddiaryapp.data.model.etc.DataResponse
import org.ybk.fooddiaryapp.data.model.diary.Diary
import org.ybk.fooddiaryapp.presentation.profile.ProfileViewModel

@RunWith(MockitoJUnitRunner::class)
class ProfileViewModelTest {
//    @get:Rule
//    var instantExecutorRule: TestRule = InstantTaskExecutorRule()
//
//    @Mock
//    private lateinit var repo: DiaryRepositoryImpl
//
//    private lateinit var vm: ProfileViewModel
//
//    private lateinit var email: String
//    private lateinit var path: String
//    private lateinit var diary: Diary
//    private lateinit var mutableLiveData: MutableLiveData<String>
//    private lateinit var diaryList: ArrayList<Diary>
//    private lateinit var localRes: Single<List<Diary>>
//    private lateinit var dataRes: DataResponse<Single<List<Diary>>, Single<Query>>
//
//    @Before
//    fun setUp() {
//        RxAndroidPlugins.setInitMainThreadSchedulerHandler{
//            Schedulers.trampoline() }
//        vm = ProfileViewModel(repo)
//        email = "ybg1485@gmail.com"
//        path = ""
//        diary = Diary()
//        diaryList = ArrayList<Diary>()
//        localRes = Single.just(diaryList)
//        dataRes = DataResponse(localRes, null)
//        mutableLiveData = MutableLiveData()
//        mocking()
//    }
//
//    private fun mocking() {
//        Mockito.`when`(repo.getDiaryAll(email))
//            .thenReturn(DataResponse(localRes, null))
//    }
//
//    @Test
//    fun testIfLiveDataHasValue() {
//        vm.getDiaryAll(email)
//        vm.diaryList.getOrAwaitValue()
//        assertThat(vm.diaryList.value, CoreMatchers.`is`(diaryList))
//    }
//    @Test
//    fun testIfRepoGetDiaryAllIsCalled_2_times() {
//        vm.getDiaryAll(email)
//        verify(repo, Mockito.times(2)).getDiaryAll(email)
//    }
//
//    @Test // 수정 필요
//    fun testIfRepoUploadProfileImageIsCalled() {
//        vm.uploadProfileImage(email, path)
//        verify(repo).uploadProfileImage(email, path, mutableLiveData)
//    }
//
//    @Test
//    fun testGetDiaryAll_local() {
//        vm.getDiaryAll(email)
//        repo.getDiaryAll(email)
//            .getLocalResponse()!!
//            .test()
//            .assertSubscribed()
//            .assertComplete()
//            .assertNoErrors()
//    }
}