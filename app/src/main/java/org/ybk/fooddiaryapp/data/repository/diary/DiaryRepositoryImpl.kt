package org.ybk.fooddiaryapp.data.repository.diary

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.UploadTask
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import org.ybk.fooddiaryapp.data.model.diary.Diary
import org.ybk.fooddiaryapp.data.model.diary.FoodImage
import org.ybk.fooddiaryapp.data.model.etc.*
import org.ybk.fooddiaryapp.data.repository.diary.datasource.DiaryLocalDataSource
import org.ybk.fooddiaryapp.data.repository.diary.datasource.DiaryRemoteDataSource
import org.ybk.fooddiaryapp.domain.repository.DiaryRepository
import org.ybk.fooddiaryapp.util.Status
import org.ybk.fooddiaryapp.util.Utils
import javax.inject.Inject
import javax.inject.Singleton

class DiaryRepositoryImpl @Inject constructor(
    private val diaryLocalDataSource: DiaryLocalDataSource,
    private val diaryRemoteDataSource: DiaryRemoteDataSource
): DiaryRepository {

    /***/
    override suspend fun addDiaryToFirestoreDB(diary: Diary): TaskResponse {
        Log.d("TAG2", "DiaryRepositoryImpl - addDiaryToFirestoreDB")
        return diaryRemoteDataSource.addDiaryToFirestoreDB(diary)
    }
    override suspend fun getDiaryFromFirestoreDB(
        email: String, registerTime: String
    ): DocumentResponse {
        Log.d("TAG2", "DiaryRepositoryImpl - getDiaryFromFirestoreDB")
        return diaryRemoteDataSource.getDiaryFromFirestoreDB(email, registerTime)
    }
    override suspend fun getDiaryListFromFirestoreDB(email: String): QueryResponse {
        Log.d("TAG2", "DiaryRepositoryImpl - getDiaryListFromFirestoreDB")
        return diaryRemoteDataSource.getDiaryListFromFirestoreDB(email)
    }
    override suspend fun addDiaryToRecommendList(diary: Diary): TaskResponse {
        Log.d("TAG2", "DiaryRepositoryImpl - addDiaryToRecommendList")
        return diaryRemoteDataSource.addDiaryToRecommendList(diary)
    }
    override suspend fun getOpenDiaryListFromFirestoreDB(open: String): QueryResponse {
        Log.d("TAG2", "DiaryRepositoryImpl - getOpenDiaryListFromFirestoreDB")
        return diaryRemoteDataSource.getOpenDiaryListFromFirestoreDB(open)
    }
    override suspend fun updateDiaryToOpenOrHideInFirestoreDB(diary: Diary): TaskResponse {
        Log.d("TAG2", "DiaryRepositoryImpl - updateDiaryToOpenInFirestoreDB")
        return diaryRemoteDataSource.updateDiaryToOpenOrHideInFirestoreDB(diary)
    }
    override suspend fun deleteDiaryFromFirestoreDB(diary: Diary): QueryResponse {
        Log.d("TAG2", "DiaryRepositoryImpl - deleteDiaryFromFirestoreDB")
        return diaryRemoteDataSource.deleteDiaryFromFirestoreDB(diary)
    }
    /***/
    override suspend fun uploadFoodImage(foodImage: FoodImage): UploadResponse {
        return diaryRemoteDataSource.uploadFoodImage(foodImage)
    }

    override suspend fun getDiary(id: String): DatabaseResponse {
        return diaryRemoteDataSource.getDiary(id)
    }

    override fun deleteDiary(diary: Diary, status: MutableLiveData<Status>) {
        if(diary.imageList.size == 0) {
            status.postValue(Status.DELETE_SUCCESS)
            return
        }
        // 데이터베이스에서 일기 내용을 지운다.
        diaryRemoteDataSource.deleteDiaryDataFromDB(diary)

        // 일기에 해당하는 이미지 리스트를 for문을 돌면서 지운다
        diary.imageList.forEachIndexed { index, foodImage ->
            val fileName = "${Utils.convertDotToDash(diary.email)}-${foodImage.registerTime}.jpeg"
            diaryRemoteDataSource.deleteImageFromStorage(fileName)
                .addOnCompleteListener {
                    if(index == diary.imageList.size - 1) {
                        status.postValue(Status.DELETE_SUCCESS)
                    }
                }
                .addOnFailureListener {
                    if(index == diary.imageList.size - 1) {
                        status.postValue(Status.DELETE_FAILED)
                    }
                }
        }
    }

    override fun insertDiariesToLocalDB(diaryList: List<Diary>): Completable {
        return diaryLocalDataSource.insertDiaries(diaryList)
    }

    override fun insertDiaryToLocalDB(diary: Diary): Completable {
        return diaryLocalDataSource.insertDiary(diary)
    }
}