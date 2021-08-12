package org.ybk.fooddiaryapp.domain.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.UploadTask
import io.reactivex.Single
import org.ybk.fooddiaryapp.data.model.etc.EventResponse
import org.ybk.fooddiaryapp.data.model.etc.ValueEventResponse

interface ProfileRepository {
    // fun getProfileImage(email: String): Single<DatabaseReference>
    suspend fun getProfileImage(email: String): ValueEventResponse

    fun uploadProfileImage(email: String, newPath: String): Single<UploadTask>

    fun addProfileImagePath(email: String, uri: String): Single<Task<Void>>
}