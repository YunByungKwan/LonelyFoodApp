package org.ybk.fooddiaryapp.domain.usecase

import com.google.android.gms.tasks.Task
import io.reactivex.Single
import org.ybk.fooddiaryapp.domain.repository.UserRepository
import javax.inject.Inject

class WithDrawFirebaseUseCase(
    private val userRepository: UserRepository
    ) {

    fun execute(): Single<Task<Void>> = userRepository.withdrawFirebase()
}