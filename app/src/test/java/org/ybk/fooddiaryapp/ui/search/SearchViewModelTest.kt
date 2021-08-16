package org.ybk.fooddiaryapp.ui.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import org.ybk.fooddiaryapp.data.repository.diary.DiaryRepositoryImpl
import org.ybk.fooddiaryapp.data.model.place.PlaceResponse
import org.ybk.fooddiaryapp.presentation.search.SearchViewModel

@RunWith(MockitoJUnitRunner::class)
class SearchViewModelTest {
//    @get:Rule
//    var instantExecutorRule: TestRule = InstantTaskExecutorRule()
//
//    @Mock
//    private lateinit var repo: DiaryRepositoryImpl
//
//    private lateinit var vm: SearchViewModel
//
//    private lateinit var keyword: String
//    private lateinit var placeResponse: PlaceResponse
//
//    @Before
//    fun set_up() {
//        RxAndroidPlugins.setInitMainThreadSchedulerHandler{
//            Schedulers.trampoline() }
//        vm = SearchViewModel(repo)
//        keyword = "홍콩반점"
//        placeRes = PlaceResponse("",0,0,0,"",listOf())
//        mocking()
//    }
//
//    private fun mocking() {
//        Mockito.`when`(repo.searchPlace(keyword))
//            .thenReturn(Single.just(placeRes))
//    }
//
//    @Test
//    fun testIfLiveDataHasValue() {
//        vm.searchPlace(keyword)
//        vm.placeRes.getOrAwaitValue()
//        assertThat(vm.placeRes.value, `is`(placeRes))
//    }
//
//    @Test
//    fun testIfRepoSearchPlaceIsCalled() {
//        Mockito.`when`(repo.searchPlace(keyword))
//            .thenReturn(Single.just(placeRes))
//        vm.searchPlace(keyword)
//        verify(repo).searchPlace(keyword)
//    }
//
//    @Test
//    fun testSearchPlace() {
//        Mockito.`when`(repo.searchPlace(keyword))
//            .thenReturn(Single.just(placeRes))
//        vm.searchPlace(keyword)
//        repo.searchPlace(keyword)
//            .test()
//            .assertSubscribed()
//            .assertComplete()
//            .assertNoErrors()
//    }
//
//    @Test
//    fun testSearchPlace_throwException() {
//        Mockito.`when`(repo.searchPlace(keyword))
//            .thenReturn(Single.error { Throwable() })
//        vm.searchPlace(keyword)
//        repo.searchPlace(keyword)
//            .test()
//            .assertSubscribed()
//            .assertNotComplete()
//    }
}