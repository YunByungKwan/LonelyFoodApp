package org.ybk.fooddiaryapp.domain.usecase

import com.google.firebase.database.DatabaseReference
import io.reactivex.Single
import org.ybk.fooddiaryapp.data.model.etc.EventResponse
import org.ybk.fooddiaryapp.data.model.etc.ValueEventResponse
import org.ybk.fooddiaryapp.domain.repository.ProfileRepository
import javax.inject.Inject

class GetProfileImageUseCase(private val profileRepository: ProfileRepository) {
    suspend fun execute(email: String): ValueEventResponse = profileRepository.getProfileImage(email)
}