package org.ybk.fooddiaryapp.presentation.di.login

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import org.ybk.fooddiaryapp.domain.usecase.GetDiaryListUseCase
import org.ybk.fooddiaryapp.domain.usecase.SignInFirebaseUseCase
import org.ybk.fooddiaryapp.presentation.di.core.ActivityScope
import org.ybk.fooddiaryapp.presentation.diary.DiaryViewModelFactory
import org.ybk.fooddiaryapp.presentation.login.LoginViewModel
import org.ybk.fooddiaryapp.presentation.login.LoginViewModelFactory

@Module
class LoginModule {
//    @Binds
//    @IntoMap
//    @ActivityScope
//    @ViewModelKey(LoginViewModel::class)
//    internal abstract fun bindViewModel(viewmodel: LoginViewModel): ViewModel

    @Provides
    fun provideLoginViewModelFactory(
        signInFirebaseUseCase: SignInFirebaseUseCase
    ): LoginViewModelFactory {
        return LoginViewModelFactory(signInFirebaseUseCase)
    }
}