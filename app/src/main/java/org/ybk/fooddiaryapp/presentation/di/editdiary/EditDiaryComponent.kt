package org.ybk.fooddiaryapp.presentation.di.editdiary

import dagger.Subcomponent
import org.ybk.fooddiaryapp.presentation.di.core.ActivityScope
import org.ybk.fooddiaryapp.presentation.editdiary.EditDiaryActivity
import org.ybk.fooddiaryapp.presentation.editdiary.EditDiaryFragment

@EditDiaryScope
@Subcomponent(modules = [EditDiaryModule::class])
interface EditDiaryComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): EditDiaryComponent
    }

    fun inject(fragment: EditDiaryFragment): EditDiaryFragment
}