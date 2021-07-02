package org.ybk.fooddiaryapp.ui.dialog.di

import dagger.Subcomponent
import org.ybk.fooddiaryapp.di.FragmentScope
import org.ybk.fooddiaryapp.ui.dialog.dot.DotDialog

@FragmentScope
@Subcomponent(modules = [DotDialogModule::class])
interface DotDialogComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): DotDialogComponent
    }

    fun inject(dialog: DotDialog): DotDialog
}