package org.ybk.fooddiaryapp.domain.repository

import androidx.lifecycle.MutableLiveData
import io.reactivex.Completable
import org.ybk.fooddiaryapp.data.model.diary.Diary
import org.ybk.fooddiaryapp.data.model.diary.FoodImage
import org.ybk.fooddiaryapp.data.model.etc.*
import org.ybk.fooddiaryapp.util.Status

interface DiaryRepository {
    /***/
    suspend fun addDiaryToFirestoreDB(diary: Diary): TaskResponse

    suspend fun getDiaryFromFirestoreDB(email: String, registerTime: String): DocumentResponse

    suspend fun getDiaryListFromFirestoreDB(email: String): QueryResponse

    suspend fun addDiaryToRecommendList(diary: Diary): TaskResponse

    suspend fun getOpenDiaryListFromFirestoreDB(open: String): QueryResponse

    suspend fun updateDiaryToOpenOrHideInFirestoreDB(diary: Diary): TaskResponse

    suspend fun deleteDiaryFromFirestoreDB(diary: Diary): QueryResponse
    /***/

    suspend fun uploadFoodImage(foodImage: FoodImage): UploadResponse

    suspend fun getDiary(id: String): DatabaseResponse

    fun deleteDiary(diary: Diary, status: MutableLiveData<Status>)

    fun insertDiariesToLocalDB(diaryList: List<Diary>): Completable

    fun insertDiaryToLocalDB(diary: Diary): Completable
}