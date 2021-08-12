package org.ybk.fooddiaryapp.presentation.di.login

import dagger.Subcomponent
import org.ybk.fooddiaryapp.presentation.di.core.ActivityScope
import org.ybk.fooddiaryapp.presentation.login.LoginActivity

@ActivityScope
@Subcomponent(modules = [LoginModule::class])
interface LoginComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): LoginComponent
    }

    fun inject(activity: LoginActivity): LoginActivity
}