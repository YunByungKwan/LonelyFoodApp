package org.ybk.fooddiaryapp.domain.usecase

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import io.reactivex.Single
import org.ybk.fooddiaryapp.domain.repository.UserRepository
import javax.inject.Inject

class SignInFirebaseUseCase(
    private val userRepository: UserRepository
    ) {

    suspend fun execute(credential: AuthCredential): AuthResult? {
        return userRepository.signInFirebaseWith(credential)
    }
}