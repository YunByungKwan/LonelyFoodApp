package org.ybk.fooddiaryapp.data.repository.user.datasourceimpl

import com.facebook.login.LoginManager
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Single
import kotlinx.coroutines.tasks.await
import org.ybk.fooddiaryapp.data.repository.user.datasource.UserRemoteDataSource
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val mAuth: FirebaseAuth
    ): UserRemoteDataSource {

    override suspend fun signInFirebaseWith(credential: AuthCredential): AuthResult? {
        try {
            return mAuth.signInWithCredential(credential).await()
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    override fun withdrawFirebase(): Single<Task<Void>> {
        LoginManager.getInstance().logOut() // Logout facebook
        return Single.just(mAuth.currentUser!!.delete())
    }
}