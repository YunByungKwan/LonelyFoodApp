package org.ybk.fooddiaryapp.domain.usecase

import org.ybk.fooddiaryapp.domain.repository.ProfileRepository
import javax.inject.Inject

class AddProfileImagePathUseCase(
    private val profileRepository: ProfileRepository
) {

    fun execute(email: String, uri: String) = profileRepository.addProfileImagePath(email, uri)
}