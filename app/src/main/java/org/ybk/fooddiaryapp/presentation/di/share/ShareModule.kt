package org.ybk.fooddiaryapp.presentation.di.share

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import org.ybk.fooddiaryapp.domain.usecase.GetOpenDiaryListUseCase
import org.ybk.fooddiaryapp.domain.usecase.WithDrawFirebaseUseCase
import org.ybk.fooddiaryapp.presentation.di.core.FragmentScope
import org.ybk.fooddiaryapp.presentation.settings.SettingsViewModelFactory
import org.ybk.fooddiaryapp.presentation.share.ShareViewModel
import org.ybk.fooddiaryapp.presentation.share.ShareViewModelFactory

@Module
class ShareModule {
//    @Binds
//    @IntoMap
//    @FragmentScope
//    @ViewModelKey(ShareViewModel::class)
//    internal abstract fun bindViewModel(viewmodel: ShareViewModel): ViewModel

    @Provides
    fun provideShareViewModelFactory(
        getOpenDiaryListUseCase: GetOpenDiaryListUseCase
    ): ShareViewModelFactory {
        return ShareViewModelFactory(getOpenDiaryListUseCase)
    }
}