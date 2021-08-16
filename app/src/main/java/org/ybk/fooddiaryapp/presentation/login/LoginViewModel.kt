package org.ybk.fooddiaryapp.presentation.login

import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.launch
import org.ybk.fooddiaryapp.base.BaseViewModel
import org.ybk.fooddiaryapp.domain.repository.DiaryRepository
import org.ybk.fooddiaryapp.domain.repository.UserRepository
import org.ybk.fooddiaryapp.domain.usecase.SignInFirebaseUseCase
import org.ybk.fooddiaryapp.util.Status
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInFirebaseUseCase: SignInFirebaseUseCase
): ViewModel() {

    private val _signResponse = MutableLiveData<Status>()
    val signResponse: LiveData<Status> get() = _signResponse

    init {
        Log.d("TAG", "LoginViewModel - init{}")
        Log.d("TAG", "${signResponse.value}")
    }

    fun signInFirebaseWithCredential(credential: AuthCredential) = viewModelScope.launch {
        Log.d("TAG", "LoginViewModel - signInFirebaseWithCredential")
        val authResult = signInFirebaseUseCase.execute(credential)

        if(authResult != null) {
            _signResponse.value = Status.SUCCESS
        } else {
            _signResponse.value = Status.FAILED
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("TAG", "LoginViewModel - onCleared()")
    }
}