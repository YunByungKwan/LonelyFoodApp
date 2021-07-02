package org.ybk.fooddiaryapp.ui.login.di

import dagger.Subcomponent
import org.ybk.fooddiaryapp.di.ActivityScope
import org.ybk.fooddiaryapp.ui.login.LoginActivity

@ActivityScope
@Subcomponent(modules = [LoginModule::class])
interface LoginComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): LoginComponent
    }

    fun inject(activity: LoginActivity): LoginActivity
}