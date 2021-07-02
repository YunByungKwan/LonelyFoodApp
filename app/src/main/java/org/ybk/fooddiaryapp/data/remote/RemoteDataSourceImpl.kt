package org.ybk.fooddiaryapp.data.remote

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.facebook.login.LoginManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import io.reactivex.Observable
import io.reactivex.Single
import org.ybk.fooddiaryapp.data.local.entity.Diary
import org.ybk.fooddiaryapp.data.local.entity.FoodImage
import org.ybk.fooddiaryapp.data.local.entity.PlaceRes
import org.ybk.fooddiaryapp.util.Constants
import org.ybk.fooddiaryapp.util.Utils
import timber.log.Timber
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val naverApiService: NaverApiService,
    private val mAuth: FirebaseAuth,
    private val mDatabase: FirebaseDatabase,
    private val mStorage: FirebaseStorage
): RemoteDataSource {

    override fun signInFirebaseWith(credential: AuthCredential): Single<Task<AuthResult>> {
        return Single.just(mAuth.signInWithCredential(credential))
    }

    override fun withdrawFirebase(): Single<Task<Void>> {
        LoginManager.getInstance().logOut() // Logout facebook
        return Single.just(mAuth.currentUser!!.delete())
    }

    override fun addDiary(diary: Diary): Single<Task<Void>> {
        val map = HashMap<String, Any>()
        map["/${Constants.DIARY_LIST}/${diary.id}"] = Utils.convertDiaryToHashMap(diary)
        val ref = mDatabase.reference.updateChildren(map)
        return Single.just(ref)
    }

    override fun uploadFoodImages(imageList: List<FoodImage>): Observable<UploadTask> {
        val imageSize = imageList.size
        val uploadTaskArray = ArrayList<UploadTask>()

        for(index in 0 until imageSize) {
            val foodImage = imageList[index]
            val imageName = Utils.getImageFileName(foodImage.email, foodImage.registerTime)
            val storageRef = mStorage.reference.child(Constants.IMAGE_FOLDER).child(imageName)
            uploadTaskArray.add(storageRef.putFile(Uri.parse(foodImage.localPath)))
        }
        return Observable.fromIterable(uploadTaskArray)
    }

    override fun getDiary(id: String): Single<DatabaseReference> {
        val ref = mDatabase.reference.child(Constants.DIARY_LIST).child(id)
        return Single.just(ref)
    }

    override fun getDiaryAll(email: String): Single<Query> {
        val ref = mDatabase.reference.child(Constants.DIARY_LIST)
        val query = ref.orderByChild(Constants.DIARY_EMAIL).equalTo(email)
        return Single.just(query)
    }

    override fun getOpenDiaryAll(open: String): Single<Query> {
        val ref = mDatabase.reference.child(Constants.DIARY_LIST)
        val query = ref.orderByChild(Constants.DIARY_IS_OPEN).equalTo(open)
        return Single.just(query)
    }

    override fun deleteDiaryDataFromDB(diary: Diary) {
        val key = "${Utils.convertDotToDash(diary.email)}-${diary.registerTime}"
        val ref = mDatabase.getReference("${Constants.DIARY_LIST}/$key")
        ref.removeValue()
    }

    override fun deleteImageFromStorage(fileName: String): Task<Void> {
        val file = mStorage.reference.child("${Constants.IMAGE_FOLDER}/$fileName")
        return file.delete()
    }

    override fun getProfileImage(email: String): Single<DatabaseReference> {
        val imageName = Utils.convertDotToDash(email)
        val parentRef = mDatabase.reference.child(Constants.PROFILE_LIST)
        val childRef = parentRef.child(imageName)
        return Single.just(childRef)
    }

    override fun uploadProfileImage(email: String, newPath: String): Single<UploadTask> {
        val imageName = Utils.convertDotToDash(email)
        val uploadTask = mStorage.reference
            .child(Constants.PROFILE_IMAGE_FOLDER)
            .child(imageName)
            .putFile(Uri.parse(newPath))

        return Single.just(uploadTask)
    }

    override fun addProfileImagePath(email: String, uri: String): Single<Task<Void>> {
        val profileMap = HashMap<String, Any>()
        profileMap[Constants.IMAGE_SERVER_PATH] = uri
        val profile = HashMap<String, Any>()
        val key = Utils.convertDotToDash(email)
        profile["/${Constants.PROFILE_LIST}/$key"] = profileMap

        return Single.just(mDatabase.reference.updateChildren(profile))
    }

    override fun searchPlace(keyword: String): Single<PlaceRes> {
        return naverApiService.searchPlace(keyword, 5, 1, "random")
    }
}