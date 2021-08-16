package org.ybk.fooddiaryapp.domain.usecase

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.ybk.fooddiaryapp.data.model.place.PlaceResponse
import org.ybk.fooddiaryapp.domain.repository.PlaceRepository

@DisplayName("[UseCase] - GetPlaceListUseCase")
internal class GetPlaceListUseCaseTest {

    @Mock
    lateinit var placeRepository: PlaceRepository

    private lateinit var getPlaceListUseCase: GetPlaceListUseCase
    private lateinit var keyword: String
    private lateinit var placeResponse: PlaceResponse

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getPlaceListUseCase = GetPlaceListUseCase(placeRepository)
        keyword = "test"
        placeResponse = PlaceResponse("",
            0, 0, 0, "", listOf())
    }

    @ExperimentalCoroutinesApi
    @DisplayName("execute()가 호출됐을 때, " +
            "PlaceRepository의 searchPlace()가 1번 호출됐는지 검증한다.")
    @Test
    fun verifySearchPlace() = runBlockingTest{
        given(placeRepository.searchPlace(keyword)).willReturn(placeResponse)
        getPlaceListUseCase.execute(keyword)
        verify(placeRepository, Mockito.times(1)).searchPlace(keyword)
    }

    @ExperimentalCoroutinesApi
    @DisplayName("execute()와 " +
            "PlaceRepository의 searchPlace()가 리턴값이 일치하는지 확인한다.")
    @Test
    fun assertSearchPlace() = runBlockingTest{
        val expected = placeResponse
        given(placeRepository.searchPlace(keyword)).willReturn(expected)
        val actual = getPlaceListUseCase.execute(keyword)
        assertThat(expected, `is`(actual))
    }
}