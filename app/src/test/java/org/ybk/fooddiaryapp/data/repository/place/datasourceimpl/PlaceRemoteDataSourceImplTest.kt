package org.ybk.fooddiaryapp.data.repository.place.datasourceimpl

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.ybk.fooddiaryapp.data.api.NaverApiService
import org.ybk.fooddiaryapp.data.model.place.PlaceResponse
import org.ybk.fooddiaryapp.data.repository.place.datasource.PlaceRemoteDataSource

@DisplayName("[RemoteDataSource] - PlaceRemoteDataSourceImpl")
internal class PlaceRemoteDataSourceImplTest {

    @Mock
    lateinit var naverApiService: NaverApiService

    private lateinit var placeRemoteDataSource: PlaceRemoteDataSource
    private lateinit var keyword: String
    private lateinit var query: String
    private val display: Int = 5
    private val start: Int = 1
    private lateinit var sort: String
    private lateinit var placeResponse: PlaceResponse

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        placeRemoteDataSource = PlaceRemoteDataSourceImpl(naverApiService)
        keyword = ""
        query = ""
        sort = "random"
        placeResponse = PlaceResponse("",
            0, 0, 0, "", listOf())
    }

    @Test
    @ExperimentalCoroutinesApi
    @DisplayName("searchPlace()가 호출됐을 때, " +
            "NaverApiService의 searchPlace()가 1번 호출됐는지 검증한다.")
    fun verifySearchPlace() = runBlockingTest {
        given(naverApiService.searchPlace(query, display, start, sort))
            .willReturn(placeResponse)
        placeRemoteDataSource.searchPlace(keyword)
        verify(naverApiService, Mockito.times(1))
            .searchPlace(query, display, start, sort)
    }


    @Test
    @ExperimentalCoroutinesApi
    @DisplayName("searchPlace()와 " +
            "NaverApiService의 searchPlace()가 리턴값이 일치하는지 확인한다.")
    fun assertSearchPlace() = runBlockingTest {
        val expected = placeResponse
        given(naverApiService.searchPlace(query, display, start, sort)).willReturn(expected)
        val actual = placeRemoteDataSource.searchPlace(keyword)
        MatcherAssert.assertThat(expected, CoreMatchers.`is`(actual))
    }
}