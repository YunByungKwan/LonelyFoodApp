package org.ybk.fooddiaryapp.domain.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import io.reactivex.Single

interface UserRepository {

    suspend fun signInFirebaseWith(credential: AuthCredential): AuthResult?

    fun withdrawFirebase(): Single<Task<Void>>
}