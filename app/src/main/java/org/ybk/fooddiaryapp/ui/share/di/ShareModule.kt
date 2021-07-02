package org.ybk.fooddiaryapp.ui.share.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import org.ybk.fooddiaryapp.di.FragmentScope
import org.ybk.fooddiaryapp.di.ViewModelKey
import org.ybk.fooddiaryapp.ui.share.ShareViewModel

@Module
abstract class ShareModule {
    @Binds
    @IntoMap
    @FragmentScope
    @ViewModelKey(ShareViewModel::class)
    internal abstract fun bindViewModel(viewmodel: ShareViewModel): ViewModel
}