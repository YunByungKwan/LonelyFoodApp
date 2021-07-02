package org.ybk.fooddiaryapp.ui.editdiary.di

import dagger.Subcomponent
import org.ybk.fooddiaryapp.di.ActivityScope
import org.ybk.fooddiaryapp.ui.editdiary.EditDiaryActivity

@ActivityScope
@Subcomponent(modules = [EditDiaryModule::class])
interface EditDiaryComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): EditDiaryComponent
    }

    fun inject(activity: EditDiaryActivity): EditDiaryActivity
}