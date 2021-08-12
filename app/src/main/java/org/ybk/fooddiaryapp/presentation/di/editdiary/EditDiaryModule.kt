package org.ybk.fooddiaryapp.presentation.di.editdiary

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import org.ybk.fooddiaryapp.domain.usecase.SignInFirebaseUseCase
import org.ybk.fooddiaryapp.presentation.di.core.ActivityScope
import org.ybk.fooddiaryapp.presentation.editdiary.EditDiaryViewModel
import org.ybk.fooddiaryapp.presentation.editdiary.EditDiaryViewModelFactory
import org.ybk.fooddiaryapp.presentation.login.LoginViewModelFactory

@Module
class EditDiaryModule {
//    @Binds
//    @IntoMap
//    @ActivityScope
//    @ViewModelKey(EditDiaryViewModel::class)
//    internal abstract fun bindViewModel(viewmodel: EditDiaryViewModel): ViewModel

    @EditDiaryScope
    @Provides
    fun provideEditDiaryViewModelFactory(
    ): EditDiaryViewModelFactory {
        return EditDiaryViewModelFactory()
    }
}