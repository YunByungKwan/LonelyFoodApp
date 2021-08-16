package org.ybk.fooddiaryapp.domain.usecase

import android.app.Activity
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.ybk.fooddiaryapp.data.model.etc.QueryResponse
import org.ybk.fooddiaryapp.domain.repository.DiaryRepository
import java.util.concurrent.Executor

@DisplayName("[UseCase] - GetDiaryListUseCase")
internal class GetDiaryListUseCaseTest {
    @Mock
    lateinit var diaryRepository: DiaryRepository

    private lateinit var getDiaryListUseCase: GetDiaryListUseCase
    private lateinit var email: String
    private lateinit var snapshot: Task<QuerySnapshot>

    @ExperimentalCoroutinesApi
    @BeforeEach
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getDiaryListUseCase = GetDiaryListUseCase(diaryRepository)
        email = "ybg1485@gmail.com"
        snapshot = object : Task<QuerySnapshot>() {
            override fun isComplete(): Boolean { TODO("Not yet implemented") }
            override fun isSuccessful(): Boolean { TODO("Not yet implemented") }
            override fun isCanceled(): Boolean { TODO("Not yet implemented") }
            override fun getResult(): QuerySnapshot { TODO("Not yet implemented") }
            override fun <X : Throwable?> getResult(p0: Class<X>): QuerySnapshot { TODO("Not yet implemented") }
            override fun getException(): Exception? { TODO("Not yet implemented") }
            override fun addOnSuccessListener(p0: OnSuccessListener<in QuerySnapshot>): Task<QuerySnapshot> { TODO("Not yet implemented") }
            override fun addOnSuccessListener(p0: Executor, p1: OnSuccessListener<in QuerySnapshot>): Task<QuerySnapshot> { TODO("Not yet implemented") }
            override fun addOnSuccessListener(p0: Activity, p1: OnSuccessListener<in QuerySnapshot>): Task<QuerySnapshot> { TODO("Not yet implemented") }
            override fun addOnFailureListener(p0: OnFailureListener): Task<QuerySnapshot> { TODO("Not yet implemented") }
            override fun addOnFailureListener(p0: Executor, p1: OnFailureListener): Task<QuerySnapshot> { TODO("Not yet implemented") }
            override fun addOnFailureListener(p0: Activity, p1: OnFailureListener): Task<QuerySnapshot> { TODO("Not yet implemented") }
        }
    }

    @ExperimentalCoroutinesApi
    @DisplayName(
        "execute()가 호출됐을 때, " +
                "DiaryRepository의 getDiaryListFromFirestoreDB()가 1번 호출됐는지 검증한다."
    )
    @Test
    fun verifyGetDiaryListFromFirestoreDBIsCalled() = runBlockingTest {
        given(diaryRepository.getDiaryListFromFirestoreDB(email))
            .willReturn(QueryResponse.Success(snapshot))

        getDiaryListUseCase.execute(email)

        Mockito
            .verify(diaryRepository, Mockito.times(1))
            .getDiaryListFromFirestoreDB(email)
    }

    @ExperimentalCoroutinesApi
    @DisplayName("execute()와 " +
            "DiaryRepository의 getDiaryListFromFirestoreDB()가 리턴값이 일치하는지 확인한다.")
    @Test
    fun assertSearchPlaceIsCalled() = runBlockingTest{
        given(diaryRepository.getDiaryListFromFirestoreDB(email))
            .willReturn(QueryResponse.Success(snapshot))

        val actual = getDiaryListUseCase.execute(email)
        MatcherAssert.assertThat(QueryResponse.Success(snapshot), `is`(actual))
    }
}