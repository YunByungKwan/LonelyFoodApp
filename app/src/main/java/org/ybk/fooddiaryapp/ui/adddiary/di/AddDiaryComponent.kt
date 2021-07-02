package org.ybk.fooddiaryapp.ui.adddiary.di

import dagger.Subcomponent
import org.ybk.fooddiaryapp.di.ActivityScope
import org.ybk.fooddiaryapp.ui.adddiary.AddDiaryActivity

@ActivityScope
@Subcomponent(modules = [AddDiaryModule::class])
interface AddDiaryComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): AddDiaryComponent
    }

    fun inject(activity: AddDiaryActivity): AddDiaryActivity
}