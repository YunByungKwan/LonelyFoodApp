package org.ybk.fooddiaryapp.presentation.di.editdiary

import dagger.Subcomponent
import org.ybk.fooddiaryapp.presentation.di.core.ActivityScope
import org.ybk.fooddiaryapp.presentation.editdiary.EditDiaryActivity

@EditDiaryScope
@Subcomponent(modules = [EditDiaryModule::class])
interface EditDiaryComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): EditDiaryComponent
    }

    fun inject(activity: EditDiaryActivity): EditDiaryActivity
}