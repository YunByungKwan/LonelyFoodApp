package org.ybk.fooddiaryapp.presentation.di.profile

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import org.ybk.fooddiaryapp.domain.usecase.AddProfileImagePathUseCase
import org.ybk.fooddiaryapp.domain.usecase.GetDiaryListUseCase
import org.ybk.fooddiaryapp.domain.usecase.GetProfileImageUseCase
import org.ybk.fooddiaryapp.domain.usecase.UploadProfileImageUseCase
import org.ybk.fooddiaryapp.presentation.di.core.FragmentScope
import org.ybk.fooddiaryapp.presentation.map.MapViewModelFactory
import org.ybk.fooddiaryapp.presentation.profile.ProfileViewModel
import org.ybk.fooddiaryapp.presentation.profile.ProfileViewModelFactory

@Module
class ProfileModule {
//    @Binds
//    @IntoMap
//    @FragmentScope
//    @ViewModelKey(ProfileViewModel::class)
//    internal abstract fun bindViewModel(viewmodel: ProfileViewModel): ViewModel

    @Provides
    fun provideProfileViewModelFactory(
        getDiaryListUseCase: GetDiaryListUseCase,
        uploadProfileImageUseCase: UploadProfileImageUseCase,
        getProfileImageUseCase: GetProfileImageUseCase,
        addProfileImagePathUseCase: AddProfileImagePathUseCase
    ): ProfileViewModelFactory {
        return ProfileViewModelFactory(
            getDiaryListUseCase,
            uploadProfileImageUseCase,
            getProfileImageUseCase,
            addProfileImagePathUseCase)
    }
}