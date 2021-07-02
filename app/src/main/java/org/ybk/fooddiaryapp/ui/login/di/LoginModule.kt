package org.ybk.fooddiaryapp.ui.login.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import org.ybk.fooddiaryapp.di.ActivityScope
import org.ybk.fooddiaryapp.di.ViewModelKey
import org.ybk.fooddiaryapp.ui.login.LoginViewModel

@Module
abstract class LoginModule {
    @Binds
    @IntoMap
    @ActivityScope
    @ViewModelKey(LoginViewModel::class)
    internal abstract fun bindViewModel(viewmodel: LoginViewModel): ViewModel
}