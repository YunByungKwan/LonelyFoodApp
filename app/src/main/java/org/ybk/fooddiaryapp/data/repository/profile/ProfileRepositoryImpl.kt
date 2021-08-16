package org.ybk.fooddiaryapp.data.repository.profile

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.UploadTask
import io.reactivex.Single
import org.ybk.fooddiaryapp.data.model.etc.EventResponse
import org.ybk.fooddiaryapp.data.model.etc.ValueEventResponse
import org.ybk.fooddiaryapp.data.repository.profile.datasource.ProfileRemoteDataSource
import org.ybk.fooddiaryapp.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val profileRemoteDataSource: ProfileRemoteDataSource
): ProfileRepository {
    override suspend fun getProfileImage(email: String): ValueEventResponse {
    return profileRemoteDataSource.getProfileImage(email)
}

    override fun uploadProfileImage(email: String, newPath: String): Single<UploadTask> {
        return profileRemoteDataSource.uploadProfileImage(email, newPath)
    }

    override fun addProfileImagePath(email: String, uri: String): Single<Task<Void>> {
        return profileRemoteDataSource.addProfileImagePath(email, uri)
    }
}