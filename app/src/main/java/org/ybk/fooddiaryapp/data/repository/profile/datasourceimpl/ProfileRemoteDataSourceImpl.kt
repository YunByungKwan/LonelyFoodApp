package org.ybk.fooddiaryapp.data.repository.profile.datasourceimpl

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import io.reactivex.Single
import org.ybk.fooddiaryapp.data.model.etc.EventResponse
import org.ybk.fooddiaryapp.data.model.etc.ValueEventResponse
import org.ybk.fooddiaryapp.data.repository.profile.datasource.ProfileRemoteDataSource
import org.ybk.fooddiaryapp.data.valueEvent
import org.ybk.fooddiaryapp.util.Constants
import org.ybk.fooddiaryapp.util.Utils
import javax.inject.Inject

class ProfileRemoteDataSourceImpl @Inject constructor(
    private val mDatabase: FirebaseDatabase,
    private val mStorage: FirebaseStorage
): ProfileRemoteDataSource {
    override suspend fun getProfileImage(email: String): ValueEventResponse {
        val imageName = Utils.convertDotToDash(email)
        val parentRef = mDatabase.reference.child(Constants.PROFILE_LIST)
        val childRef = parentRef.child(imageName)
        return childRef.valueEvent()
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
}