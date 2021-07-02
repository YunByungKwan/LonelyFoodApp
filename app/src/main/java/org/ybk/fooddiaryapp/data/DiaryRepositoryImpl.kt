package org.ybk.fooddiaryapp.data

import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query
import com.google.firebase.storage.UploadTask
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import org.ybk.fooddiaryapp.data.local.LocalDataSource
import org.ybk.fooddiaryapp.data.local.entity.DataResponse
import org.ybk.fooddiaryapp.data.local.entity.Diary
import org.ybk.fooddiaryapp.data.local.entity.FoodImage
import org.ybk.fooddiaryapp.data.local.entity.PlaceRes
import org.ybk.fooddiaryapp.data.remote.RemoteDataSource
import org.ybk.fooddiaryapp.di.Local
import org.ybk.fooddiaryapp.di.Remote
import org.ybk.fooddiaryapp.util.Status
import org.ybk.fooddiaryapp.util.Utils
import org.ybk.fooddiaryapp.util.compat.NetworkCompat
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DiaryRepositoryImpl @Inject constructor(
    @Local private val localDataSource: LocalDataSource,
    @Remote private val remoteDataSource: RemoteDataSource
): DiaryRepository {

    override fun signInFirebaseWith(credential: AuthCredential): Single<Task<AuthResult>> {
        return remoteDataSource.signInFirebaseWith(credential)
    }

    override fun withdrawFirebase(): Single<Task<Void>> {
        return remoteDataSource.withdrawFirebase()
    }

    override fun addDiary(diary: Diary): Single<Task<Void>> {
        return remoteDataSource.addDiary(diary)
    }

    override fun uploadFoodImages(imageList: List<FoodImage>): Observable<UploadTask> {
        return remoteDataSource.uploadFoodImages(imageList)
    }

    override fun getDiary(id: String): DataResponse<Single<Diary>, Single<DatabaseReference>> {
        return if(NetworkCompat.isConnected()) {
            DataResponse(null, remoteDataSource.getDiary(id))
        } else {
            DataResponse(localDataSource.getDiary(id), null)
        }
    }

    override fun getDiaryAll(email: String): DataResponse<Single<List<Diary>>, Single<Query>> {
        return if(NetworkCompat.isConnected()) {
            DataResponse(null,remoteDataSource.getDiaryAll(email))
        } else {
            DataResponse(localDataSource.getDiaryAll(email), null)
        }
    }

    override fun getOpenDiaryAll(
        open: String
    ): DataResponse<Single<List<Diary>>, Single<Query>> {
        return if(NetworkCompat.isConnected()) {
            DataResponse(null, remoteDataSource.getOpenDiaryAll(open))
        } else {
            DataResponse(localDataSource.getOpenDiaryAll(open), null)
        }
    }

    override fun deleteDiary(diary: Diary, status: MutableLiveData<Status>) {
        if(diary.imageList.size == 0) {
            status.postValue(Status.DELETE_SUCCESS)
            return
        }
        // 데이터베이스에서 일기 내용을 지운다.
        remoteDataSource.deleteDiaryDataFromDB(diary)

        // 일기에 해당하는 이미지 리스트를 for문을 돌면서 지운다
        diary.imageList.forEachIndexed { index, foodImage ->
            val fileName = "${Utils.convertDotToDash(diary.email)}-${foodImage.registerTime}.jpeg"
            remoteDataSource.deleteImageFromStorage(fileName)
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

    override fun getProfileImage(email: String): Single<DatabaseReference> {
        return remoteDataSource.getProfileImage(email)
    }

    override fun uploadProfileImage(email: String, newPath: String): Single<UploadTask> {
        return remoteDataSource.uploadProfileImage(email, newPath)
    }

    override fun addProfileImagePath(email: String, uri: String): Single<Task<Void>> {
        return remoteDataSource.addProfileImagePath(email, uri)
    }

    override fun searchPlace(keyword: String): Single<PlaceRes> {
        return remoteDataSource.searchPlace(keyword)
    }

    override fun insertDiariesToLocalDB(diaryList: List<Diary>): Completable {
        return localDataSource.insertDiaries(diaryList)
    }

    override fun insertDiaryToLocalDB(diary: Diary): Completable {
        return localDataSource.insertDiary(diary)
    }
}