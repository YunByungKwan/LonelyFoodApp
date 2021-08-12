package org.ybk.fooddiaryapp.domain.usecase

import com.google.firebase.storage.UploadTask
import io.reactivex.Single
import org.ybk.fooddiaryapp.domain.repository.ProfileRepository
import javax.inject.Inject

class UploadProfileImageUseCase(
    private val profileRepository: ProfileRepository
    ) {

    fun execute(email: String, newPath: String): Single<UploadTask> {
        return profileRepository.uploadProfileImage(email, newPath)
    }
}