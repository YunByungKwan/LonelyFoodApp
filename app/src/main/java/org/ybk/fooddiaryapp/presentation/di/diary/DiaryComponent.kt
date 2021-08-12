package org.ybk.fooddiaryapp.presentation.di.diary

import dagger.Subcomponent
import org.ybk.fooddiaryapp.presentation.di.core.FragmentScope
import org.ybk.fooddiaryapp.presentation.diary.DiaryFragment

@DiaryScope
@Subcomponent(modules = [DiaryModule::class])
interface DiaryComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): DiaryComponent
    }

    fun inject(fragment: DiaryFragment): DiaryFragment
}