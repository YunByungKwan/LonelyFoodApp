package org.ybk.fooddiaryapp.ui.profile.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import org.ybk.fooddiaryapp.di.FragmentScope
import org.ybk.fooddiaryapp.di.ViewModelKey
import org.ybk.fooddiaryapp.ui.profile.ProfileViewModel

@Module
abstract class ProfileModule {
    @Binds
    @IntoMap
    @FragmentScope
    @ViewModelKey(ProfileViewModel::class)
    internal abstract fun bindViewModel(viewmodel: ProfileViewModel): ViewModel
}