package org.ybk.fooddiaryapp.ui.diary.di

import dagger.Subcomponent
import org.ybk.fooddiaryapp.di.FragmentScope
import org.ybk.fooddiaryapp.ui.diary.DiaryFragment

@FragmentScope
@Subcomponent(modules = [DiaryModule::class])
interface DiaryComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): DiaryComponent
    }

    fun inject(fragment: DiaryFragment): DiaryFragment
}