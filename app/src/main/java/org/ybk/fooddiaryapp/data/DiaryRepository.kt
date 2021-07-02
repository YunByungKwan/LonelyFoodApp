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
import org.ybk.fooddiaryapp.data.local.entity.Diary
import org.ybk.fooddiaryapp.data.local.entity.FoodImage
import org.ybk.fooddiaryapp.data.local.entity.PlaceRes
import org.ybk.fooddiaryapp.data.local.entity.DataResponse
import org.ybk.fooddiaryapp.util.Status

interface DiaryRepository {

    fun signInFirebaseWith(credential: AuthCredential): Single<Task<AuthResult>>

    fun withdrawFirebase(): Single<Task<Void>>

    fun addDiary(diary: Diary): Single<Task<Void>>

    fun uploadFoodImages(imageList: List<FoodImage>): Observable<UploadTask>

    fun getDiary(id: String): DataResponse<Single<Diary>, Single<DatabaseReference>>

    fun getDiaryAll(email: String): DataResponse<Single<List<Diary>>, Single<Query>>

    fun getOpenDiaryAll(open: String): DataResponse<Single<List<Diary>>, Single<Query>>

    fun deleteDiary(diary: Diary, status: MutableLiveData<Status>)

    fun getProfileImage(email: String): Single<DatabaseReference>

    fun uploadProfileImage(email: String, newPath: String): Single<UploadTask>

    fun addProfileImagePath(email: String, uri: String): Single<Task<Void>>

    fun searchPlace(keyword: String): Single<PlaceRes>

    fun insertDiariesToLocalDB(diaryList: List<Diary>): Completable

    fun insertDiaryToLocalDB(diary: Diary): Completable
}