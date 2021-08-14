package org.ybk.fooddiaryapp.data.repository.diary.datasource

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.UploadTask
import io.reactivex.Observable
import io.reactivex.Single
import org.ybk.fooddiaryapp.data.model.diary.Diary
import org.ybk.fooddiaryapp.data.model.diary.FoodImage
import org.ybk.fooddiaryapp.data.model.etc.*
import org.ybk.fooddiaryapp.data.model.place.PlaceRes
import org.ybk.fooddiaryapp.util.Status

interface DiaryRemoteDataSource {

    /***/
    suspend fun addDiaryToFirestoreDB(diary: Diary): TaskResponse

    suspend fun getDiaryFromFirestoreDB(email: String, registerTime: String): DocumentResponse

    suspend fun getDiaryListFromFirestoreDB(email: String): QueryResponse

    suspend fun getOpenDiaryListFromFirestoreDB(open: String): QueryResponse

    suspend fun addDiaryToRecommendList(diary: Diary): TaskResponse

    suspend fun updateDiaryToOpenOrHideInFirestoreDB(diary: Diary): TaskResponse

    suspend fun deleteDiaryFromRecommendList(diary: Diary)

    suspend fun deleteDiaryFromFirestoreDB(diary: Diary): QueryResponse
    /***/

    suspend fun uploadFoodImage(foodImage: FoodImage): UploadResponse

    suspend fun getDiary(id: String): DatabaseResponse

    fun deleteDiaryDataFromDB(diary: Diary)

    fun deleteImageFromStorage(fileName: String): Task<Void>
}