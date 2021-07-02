package org.ybk.fooddiaryapp.data.remote

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query
import com.google.firebase.storage.UploadTask
import io.reactivex.Observable
import io.reactivex.Single
import org.ybk.fooddiaryapp.data.local.entity.Diary
import org.ybk.fooddiaryapp.data.local.entity.FoodImage
import org.ybk.fooddiaryapp.data.local.entity.PlaceRes

interface RemoteDataSource {

    fun signInFirebaseWith(credential: AuthCredential): Single<Task<AuthResult>>

    fun withdrawFirebase(): Single<Task<Void>>

    fun addDiary(diary: Diary): Single<Task<Void>>

    fun uploadFoodImages(imageList: List<FoodImage>): Observable<UploadTask>

    fun getDiary(id: String): Single<DatabaseReference>

    fun getDiaryAll(email: String): Single<Query>

    fun getOpenDiaryAll(open: String): Single<Query>

    fun deleteDiaryDataFromDB(diary: Diary)

    fun deleteImageFromStorage(fileName: String): Task<Void>

    fun getProfileImage(email: String): Single<DatabaseReference>

    fun uploadProfileImage(email: String, newPath: String): Single<UploadTask>

    fun addProfileImagePath(email: String, uri: String): Single<Task<Void>>

    fun searchPlace(keyword: String): Single<PlaceRes>
}