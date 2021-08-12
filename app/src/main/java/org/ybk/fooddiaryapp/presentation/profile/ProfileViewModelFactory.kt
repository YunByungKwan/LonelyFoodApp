package org.ybk.fooddiaryapp.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.ybk.fooddiaryapp.domain.usecase.AddProfileImagePathUseCase
import org.ybk.fooddiaryapp.domain.usecase.GetDiaryListUseCase
import org.ybk.fooddiaryapp.domain.usecase.GetProfileImageUseCase
import org.ybk.fooddiaryapp.domain.usecase.UploadProfileImageUseCase

class ProfileViewModelFactory(
    private val getDiaryListUseCase: GetDiaryListUseCase,
    private val uploadProfileImageUseCase: UploadProfileImageUseCase,
    private val getProfileImageUseCase: GetProfileImageUseCase,
    private val addProfileImagePathUseCase: AddProfileImagePathUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProfileViewModel(
            getDiaryListUseCase,
            uploadProfileImageUseCase,
            getProfileImageUseCase,
            addProfileImagePathUseCase) as T
    }
}