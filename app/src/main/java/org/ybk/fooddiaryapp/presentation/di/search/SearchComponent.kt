package org.ybk.fooddiaryapp.presentation.di.search

import dagger.Subcomponent
import org.ybk.fooddiaryapp.presentation.di.core.ActivityScope
import org.ybk.fooddiaryapp.presentation.search.SearchActivity

@ActivityScope
@Subcomponent(modules = [SearchModule::class])
interface SearchComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): SearchComponent
    }

    fun inject(activity: SearchActivity): SearchActivity
}