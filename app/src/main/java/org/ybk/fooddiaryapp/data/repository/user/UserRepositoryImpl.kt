package org.ybk.fooddiaryapp.data.repository.user

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import io.reactivex.Single
import org.ybk.fooddiaryapp.data.repository.user.datasource.UserRemoteDataSource
import org.ybk.fooddiaryapp.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource
): UserRepository {
    override suspend fun signInFirebaseWith(credential: AuthCredential): AuthResult? {
        return userRemoteDataSource.signInFirebaseWith(credential)
    }

    override fun withdrawFirebase(): Single<Task<Void>> {
        return userRemoteDataSource.withdrawFirebase()
    }
}