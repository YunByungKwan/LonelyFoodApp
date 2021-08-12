package org.ybk.fooddiaryapp.presentation.di.search

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import org.ybk.fooddiaryapp.presentation.di.core.ActivityScope
import org.ybk.fooddiaryapp.domain.usecase.*
import org.ybk.fooddiaryapp.presentation.profile.ProfileViewModelFactory
import org.ybk.fooddiaryapp.presentation.search.SearchViewModel
import org.ybk.fooddiaryapp.presentation.search.SearchViewModelFactory

@Module
class SearchModule {
//    @Binds
//    @IntoMap
//    @ActivityScope
//    @ViewModelKey(SearchViewModel::class)
//    internal abstract fun bindViewModel(viewmodel: SearchViewModel): ViewModel

    @Provides
    fun provideSearchViewModelFactory(
        getPlaceListUseCase: GetPlaceListUseCase
    ): SearchViewModelFactory {
        return SearchViewModelFactory(getPlaceListUseCase)
    }
}