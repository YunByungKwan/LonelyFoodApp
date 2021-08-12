package org.ybk.fooddiaryapp.presentation.di.dialog

import dagger.Subcomponent
import org.ybk.fooddiaryapp.presentation.di.core.FragmentScope
import org.ybk.fooddiaryapp.presentation.dialog.DotDialog

@DotDialogScope
@Subcomponent(modules = [DotDialogModule::class])
interface DotDialogComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): DotDialogComponent
    }

    fun inject(dialog: DotDialog): DotDialog
}