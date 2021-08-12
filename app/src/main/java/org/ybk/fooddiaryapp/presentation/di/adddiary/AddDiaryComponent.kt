package org.ybk.fooddiaryapp.presentation.di.adddiary

import dagger.Subcomponent
import org.ybk.fooddiaryapp.presentation.adddiary.AddDiaryActivity
import org.ybk.fooddiaryapp.presentation.adddiary.AddDiaryFragment
import org.ybk.fooddiaryapp.presentation.di.core.ActivityScope

@AddDiaryScope
@Subcomponent(modules = [AddDiaryModule::class])
interface AddDiaryComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): AddDiaryComponent
    }

    fun inject(activity: AddDiaryActivity): AddDiaryActivity
    fun inject2(fragment: AddDiaryFragment): AddDiaryFragment
}