package org.ybk.fooddiaryapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.AuthCredential
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.ybk.fooddiaryapp.base.BaseViewModel
import org.ybk.fooddiaryapp.data.DiaryRepository
import org.ybk.fooddiaryapp.util.Status
import timber.log.Timber
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val repository: DiaryRepository
): BaseViewModel() {

    private val _signResponse = MutableLiveData<Status>()
    val signResponse: LiveData<Status> get() = _signResponse

    fun signInFirebaseWithCredential(credential: AuthCredential) {
        addDisposable(
            repository.signInFirebaseWith(credential)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({task ->
                    task.addOnCompleteListener { result ->
                        if(result.isSuccessful) {
                            _signResponse.postValue(Status.SUCCESS)
                        } else {
                            _signResponse.postValue(Status.FAILED)
                        }
                    }.addOnFailureListener {
                        _signResponse.postValue(Status.FAILED)
                    }
                }) {
                    _signResponse.postValue(Status.FAILED)
                }
        )
    }
}