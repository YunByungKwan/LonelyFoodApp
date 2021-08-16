package org.ybk.fooddiaryapp.presentation.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.rules.TestRule
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.ybk.fooddiaryapp.CoroutinesTestRule
import org.ybk.fooddiaryapp.MainCoroutineExtension
import org.ybk.fooddiaryapp.MainCoroutineRule
import org.ybk.fooddiaryapp.data.model.place.Place
import org.ybk.fooddiaryapp.data.model.place.PlaceResponse
import org.ybk.fooddiaryapp.domain.repository.PlaceRepository
import org.ybk.fooddiaryapp.domain.usecase.GetPlaceListUseCase

@ExperimentalCoroutinesApi
@DisplayName("[ViewModel] - SearchViewModel")
internal class SearchViewModelTest {
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var getPlaceListUseCase: GetPlaceListUseCase

    lateinit var viewModel: SearchViewModel
    private lateinit var keyword: String
    private lateinit var placeResponse: PlaceResponse

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = SearchViewModel(getPlaceListUseCase, coroutinesTestRule.testDispatcher)
        keyword = "test"
        placeResponse = PlaceResponse("",
            0, 0, 0, "", listOf())
    }

//    @Test
//    fun test() = coroutinesTestRule.testDispatcher.runBlockingTest {
//        `when`(getPlaceListUseCase.execute(keyword)).thenReturn(placeResponse)
//        viewModel.searchPlace(keyword)
//    }
}