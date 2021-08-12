package org.ybk.fooddiaryapp.data.repository.user.datasource

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import io.reactivex.Single

interface UserRemoteDataSource {

    suspend fun signInFirebaseWith(credential: AuthCredential): AuthResult?

    fun withdrawFirebase(): Single<Task<Void>>

}