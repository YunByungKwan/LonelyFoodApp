package org.ybk.fooddiaryapp.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.ybk.fooddiaryapp.domain.usecase.SignInFirebaseUseCase

class LoginViewModelFactory(
    private val signInFirebaseUseCase: SignInFirebaseUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(signInFirebaseUseCase) as T
    }
}