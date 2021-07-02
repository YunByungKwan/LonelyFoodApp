package org.ybk.fooddiaryapp.ui.search.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import org.ybk.fooddiaryapp.di.ActivityScope
import org.ybk.fooddiaryapp.di.ViewModelKey
import org.ybk.fooddiaryapp.ui.search.SearchViewModel

@Module
abstract class SearchModule {
    @Binds
    @IntoMap
    @ActivityScope
    @ViewModelKey(SearchViewModel::class)
    internal abstract fun bindViewModel(viewmodel: SearchViewModel): ViewModel
}