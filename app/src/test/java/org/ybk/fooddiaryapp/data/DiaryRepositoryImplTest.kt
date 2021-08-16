package org.ybk.fooddiaryapp.data

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query
import io.reactivex.Single
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.ybk.fooddiaryapp.data.model.etc.DataResponse
import org.ybk.fooddiaryapp.data.model.diary.Diary
import org.ybk.fooddiaryapp.data.repository.diary.DiaryRepositoryImpl

@RunWith(MockitoJUnitRunner::class)
class DiaryRepositoryImplTest {

//    @Mock
//    private lateinit var repository: DiaryRepositoryImpl
//
//
//    private lateinit var email: String
//    private lateinit var registerTime: String
//    private lateinit var open: String
//    private lateinit var diary: Diary
//    private lateinit var task: Task<Void>
//
//    @Before
//    fun set_up() {
//        email = "ybg1485@gmail.com"
//        registerTime = "1354216441"
//        open = "Y"
//        diary = Diary()
//    }
//
//    /**================================== getDiaryAll() ==========================================*/
//    @Test
//    fun test_if_getDiaryAll_return_LocalReponse() {
//        val local = Single.just(listOf(diary))
//        val remote: Single<Query>? = null
//        val response = DataResponse(local, remote)
//
//        Mockito.`when`(repository.getDiaryAll(email)).thenReturn(response)
//
//        val expect = response.getLocalResponse()
//        val actual = repository.getDiaryAll(email).getLocalResponse()
//        assertThat(actual, `is`(expect))
//    }
//
//    @Test
//    fun test_getDiaryAll_assert_chaining_about_LocalResponse() {
//        val local = Single.just(listOf(diary))
//        val remote: Single<Query>? = null
//        val response = DataResponse(local, remote)
//
//        Mockito.`when`(repository.getDiaryAll(email)).thenReturn(response)
//
//        repository.getDiaryAll(email).getLocalResponse()!!
//            .test()
//            .assertSubscribed()
//            .assertComplete()
//            .assertNoErrors()
//    }
//
//    @Test
//    fun test_if_getDiaryAll_return_RemoteReponse() {
//    }
//
//    @Test
//    fun test_getDiaryAll_assert_chaining_about_RemoteResponse() {}
//
//    /**================================= getOpenDiaryAll() =======================================*/
//    @Test
//    fun test_if_getOpenDiaryAll_return_LocalReponse() {
//        val local = Single.just(listOf(diary))
//        val remote: Single<Query>? = null
//        val response = DataResponse(local, remote)
//
//        Mockito.`when`(repository.getOpenDiaryAll(open)).thenReturn(response)
//
//        val actual = repository.getOpenDiaryAll(open).getLocalResponse()
//        val expect = response.getLocalResponse()
//
//        assertThat(actual, `is`(expect))
//    }
//
//    @Test
//    fun test_getOpenDiaryAll_assert_chaining_about_LocalResponse() {
//        val local = Single.just(listOf(diary))
//        val remote: Single<Query>? = null
//        val response = DataResponse(local, remote)
//
//        Mockito.`when`(repository.getOpenDiaryAll(email)).thenReturn(response)
//
//        repository.getOpenDiaryAll(email).getLocalResponse()!!
//            .test()
//            .assertSubscribed()
//            .assertComplete()
//            .assertNoErrors()
//    }
//
//    @Test
//    fun test_if_getOpenDiaryAll_return_RemoteReponse() {
//
//    }
//
//    @Test
//    fun test_getOpenDiaryAll_assert_chaining_about_RemoteResponse() {
//
//    }
//
//    /**==================================== getDiary() ===========================================*/
//    @Test
//    fun test_if_getDiary_return_LocalReponse() {
//        val local = Single.just(Diary())
//        val remote: Single<DatabaseReference>? = null
//        val response = DataResponse(local, remote)
//
//        Mockito.`when`(repository.getDiary(email)).thenReturn(response)
//
//        val actual = repository.getDiary(email).getLocalResponse()
//        val expect = response.getLocalResponse()
//
//        assertThat(actual, `is`(expect))
//    }
//
//    @Test
//    fun test_getDiary_assert_chaining_about_LocalResponse() {
//        val local = Single.just(Diary())
//        val remote: Single<DatabaseReference>? = null
//        val response = DataResponse(local, remote)
//
//        Mockito.`when`(repository.getDiary(email)).thenReturn(response)
//
//        repository.getDiary(email).getLocalResponse()!!
//            .test()
//            .assertSubscribed()
//            .assertValues(response.getLocalResponse()!!.blockingGet())
//            .assertComplete()
//            .assertNoErrors()
//    }
//
//    @Test
//    fun test_if_getDiary_return_RemoteReponse() {
//
//    }
//
//    @Test
//    fun test_getDiary_assert_chaining_about_RemoteResponse() {
//
//    }
//
//    /**================================ addDiary() ===============================================*/
//
//    @Test
//    fun test_if_addDiary() {
//    }
//
//    /**================================= editDiary() =============================================*/
//
//    @Test
//    fun test_if_editDiary() {
//
//    }
}