package org.ybk.fooddiaryapp.ui.settings

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.ybk.fooddiaryapp.data.repository.diary.DiaryRepositoryImpl
import org.ybk.fooddiaryapp.presentation.settings.SettingsViewModel

@RunWith(MockitoJUnitRunner::class)
class SettingsViewModelTest {
//    @get:Rule
//    var instantExecutorRule: TestRule = InstantTaskExecutorRule()
//
//    @Mock
//    private lateinit var repo: DiaryRepositoryImpl
//
//    private lateinit var vm: SettingsViewModel
//
//    @Before
//    fun setUp() {
//        RxAndroidPlugins.setInitMainThreadSchedulerHandler{
//            Schedulers.trampoline() }
//        vm = SettingsViewModel(repo)
//        mocking()
//    }
//
//    private fun mocking() {
//
//    }
//
//    @Test
//    fun testIfRepoWithdrawFirebaseIsCalled() {
//
//    }
}